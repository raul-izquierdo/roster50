# Changelog

All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.1.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).


## [1.0.1](https://github.com/raul-izquierdo/roster50/compare/v1.0.0...v1.0.1) - 2026/09/19

### Fixed

- A misleading message indicating that the teams were up to date, when this tool has nothing to do with teams, has been
  changed to "roster is up to date".

## [1.0.0]


### Added

- Compara dos ficheros de roster de Classroom 50 y muestra las diferencias entre ellos.
- Genera un fichero con los alumnos a importar manualmente en Classroom 50.
- Indica los alumnos que hay que eliminar del roster a través del interfaz web de Classroom 50.
- Indica los alumnos que han cambiado de grupo y que hay que actualizar a través del interfaz web de Classroom 50.
- El roster actual puede bajarse a mano desde Classroom 50 o bien dar los parámetros (token, organización y classroom) para que se baje automáticamente.
- Indica las entradas del roster (tanto del antiguo como del nuevo) que no cumplen el formato esperado y que por tanto no se pueden procesar.
