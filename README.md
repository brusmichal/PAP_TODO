# PAP21Z-Z25

### Uruchomienie Aplikacji
#### Instalacja Dockera
Aplikacja korzysta ze skonteneryzowanej bazy danych, więc w celu jej 
poprawnego uruchomienia należy uprzednio zainstalować dockera
([link do instalacji](https://docs.docker.com/desktop/windows/install/)).

#### Uruchomienie aplikacji
Należy upewnić się, że docker jest włączony a następnie użyć **komendy**:
```bash
docker-compose -f <ścieżka folderu z aplikacją>/src/main/resources/docker-compose.yml up -d
```
Po uruchomieniu bazy danych należy wykonać polecenie:
```bash
[WINDOWS] ./gradlew bootRun
[UNIX] gradlew bootRun
```


### Testy
Testy aplikacji wykonywane są po wykonaniu polecenia:
```bash
[WINDOWS] ./gradlew test
[UNIX] gradlew test 
```
W celu wykonania testów integracyjnych dla bazy danych no SQL należy uruchomić polecenie:
```bash
[WINDOWS] ./gradlew databaseTest
[UNIX] gradlew databaseTest 
```