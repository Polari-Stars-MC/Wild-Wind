param(
    [Parameter(Mandatory = $true)] [string]$Name,
    [Parameter(Mandatory = $true)] [string]$Id,
    [string]$Package = "polari_stars.wild_wind.$Id",
    [string]$DisplayName = $Name,
    [string]$Version = "1.0.0",
    [string]$Description = "Example module description.",
    [switch]$Force
)

if ($Id -notmatch '^[a-z0-9]+(?:_[a-z0-9]+)*$') {
    throw "Id must contain lowercase letters, numbers, and underscores only: $Id"
}
if ($Package -notmatch '^[a-z][a-z0-9_]*(\.[a-z][a-z0-9_]*)*$') {
    throw "Package must be a valid lowercase Java package: $Package"
}
if ($DisplayName -match '[\"\r\n]' -or $Description -match '[\"\r\n]') {
    throw 'DisplayName and Description cannot contain quotes or newlines.'
}

$root = (Resolve-Path (Join-Path $PSScriptRoot '..')).Path
$utf8NoBom = New-Object System.Text.UTF8Encoding($false)
function Write-Utf8NoBom([string]$Path, [string]$Content) {
    [System.IO.File]::WriteAllText($Path, $Content, $script:utf8NoBom)
}
$moduleDir = Join-Path $root "modules\$Name"
if ((Test-Path -LiteralPath $moduleDir) -and -not $Force) {
    throw "Module already exists: $moduleDir. Use -Force to regenerate generated files."
}

$className = (($Id -split '[-_]') | ForEach-Object { $_.Substring(0, 1).ToUpper() + $_.Substring(1) }) -join ''
$catalogPrefix = (($Id -split '[-_]') | ForEach-Object { $_.Substring(0, 1).ToUpper() + $_.Substring(1) }) -join ''
$rootCatalogPath = "$root\gradle\libs.versions.toml"
$rootCatalog = Get-Content -Raw $rootCatalogPath
$existingPrefix = [regex]::Match($rootCatalog, ('(?m)^([A-Za-z][A-Za-z0-9]*)Id\s*=\s*"' + [regex]::Escape($Id) + '"')).Groups[1].Value
if ($existingPrefix) {
    $catalogPrefix = $existingPrefix
}
$packagePath = $Package.Replace('.', '\')
New-Item -ItemType Directory -Force -Path "$moduleDir\src\java\$packagePath", "$moduleDir\src\java\templates\META-INF", "$moduleDir\src\java\resources\assets" | Out-Null
$build = Get-Content -Raw "$root\templates\module\build.gradle"
$build = $build.Replace('versions.moduleId', "versions.${catalogPrefix}Id").Replace('versions.moduleName', "versions.${catalogPrefix}Name").Replace('versions.moduleVersion', "versions.${catalogPrefix}Version").Replace('versions.moduleGroup', "versions.${catalogPrefix}Group").Replace('versions.moduleDescription', "versions.${catalogPrefix}Description")
Write-Utf8NoBom "$moduleDir\build.gradle" $build
Copy-Item -LiteralPath "$root\templates\module\src\java\templates\META-INF\neoforge.mods.toml" -Destination "$moduleDir\src\java\templates\META-INF\neoforge.mods.toml" -Force
if (($rootCatalog -match "(?m)^${catalogPrefix}Id\s*=") -and -not $Force) {
    throw "Version catalog entries already exist for module: $Id"
}
if ($rootCatalog -notmatch "(?m)^${catalogPrefix}Id\s*=") {
    $catalogEntries = @"
${catalogPrefix}Id = "$Id"
${catalogPrefix}Name = "$DisplayName"
${catalogPrefix}Version = "$Version"
${catalogPrefix}Group = "$Package"
${catalogPrefix}Description = "$Description"

[bundles]
"@
    $rootCatalog = $rootCatalog.Replace('[bundles]', $catalogEntries.Trim())
    Write-Utf8NoBom $rootCatalogPath $rootCatalog
}

$mixins = Get-Content -Raw "$root\templates\module\src\java\resources\assets\MODULE_ID.mixins.json"
$mixins = $mixins.Replace('MODULE_ID', $Id).Replace('MODULE_PACKAGE', $Package)
Write-Utf8NoBom "$moduleDir\src\java\resources\assets\$Id.mixins.json" $mixins
$java = Get-Content -Raw "$root\templates\module\src\java\MODULE_PACKAGE\__MODULE_CLASS__.java"
$java = $java.Replace('__MODULE_PACKAGE__', $Package).Replace('__MODULE_ID__', $Id).Replace('__MODULE_CLASS__', $className)
Write-Utf8NoBom "$moduleDir\src\java\$packagePath\$className.java" $java

$settings = Get-Content -Raw "$root\settings.gradle"
$includeLine = "include(':$Id')"
if ($settings -notmatch [regex]::Escape($includeLine) -and $settings -notmatch [regex]::Escape("modules/$Name")) {
    Add-Content -LiteralPath "$root\settings.gradle" -Value "`r`n$includeLine`r`nproject(':$Id').projectDir = file('modules/$Name')`r`n"
}

Write-Host "Created module '$DisplayName' at $moduleDir"
Write-Host "The module depends on the root lib project via implementation project(':')."
