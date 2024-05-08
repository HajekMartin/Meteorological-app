# Dokumentace
Zde dokumentuji postup řešení pro odevzdání. 

## Požadavky na technické řešení
1. Maven pro sestavení - splněno
2. Spring Boot jako implementační framework - splněno

## Datový model (persistence)
Data ukládám do h2 in-memory databáze. Pro takovýto projekt by to měl být dostačující. Po ukončení aplikace je h2 nastavená tak, že se nesmaže, takže při opětovném spuštění se načtou její data co byla již vytvořena.
1. Stát
   * (id - String, name - String)
   * Stát není nijak propojen s OpenWeather Api.
2. Město
   * (id - Long, name - String, stateId - String)
   * Atribut id musí být validní a musí pro něj jít stáhnout data z OpenWeatherApi. Použiji dotaz (https://openweathermap.org/current#cityid), který pokud selže město se nepřidá. Tzn. id musí být takové co je z tohoto JSON souboru co jde stáhnout https://bulk.openweathermap.org/sample/city.list.json.gz.
   * Název města je již libovolný ten se neověřujě lze si tedy nastavit město Liberec a mít pro něj popis např. "Počasí u babičky".
   * ID státu je String. Před zadáním města musí být již v DB stát zadaný jinak nejde město přidat.
3. Měření
   * (id - Long, date - Long, cityId - Long, temperature - Double, rain - Double, windSpeed - double)
   * ID měření je automaticky generováno.
   * Datum je long a musí být zadané v sekundovém formátu Unix timstampu. Já pro to používal vdy tuto stránku https://www.unixtimestamp.com/. 
   * ID města musí být stejný jako bylo zadáno u města. Tzn. to id z OpenWeatherApi.
   * Meteorologické hodnoty jsou double a musí být zadány vždy všechny! Žádná nemůže být null.

## API
Obsahuje snad vše co se týče zadání tzn CRUD je možné volat přes API. Já to testoval přes Postmana. Všechny své kolekce k testování jsem exportoval do složky [Postman](./postman) a jsou vyexportované v Collection v2.1.

## REST
Jak jsem zmínil obsahuje to zmíněné CRUD, pro zadání je zde i metoda pro aktuální (metoda actual) ta vrátí poslední stažené měření pro dané město. Průměr hodnot není daný jako 3 metody (pro den, týden a 14 dní), ale je to metoda average, která má parametr id města a duration (čas v sekundách) za jakou dobu se má počítat průměr.

## Testování
Všechny operace (jakoby endpointy HTTP) mají své testy + mám testovanou většinu service.

## Logování
Logování probíhá do souboru log.out

## Sestavení
Pomocí maven by mělo být možné sestavit. Mně to fungovalo.

## CSV import a export
Aplikace má jak REST API tak kompletní funkcionalitu nabízí i v podobě MVC. Není tedy nutné vůbec API používat. Je zde složka [csv](./csv), kde jsou města s názvem a id, a z nich stažená data. Jsou tam i nesmyslné hodnoty, které jsem si přidával během testování.

## Data
Data stahuje z OpenWeather a vždy je stahuji přes jejich id jak jsem zde již zmínil. Stahování je možné konfigurovat v application.properties.

## Další požadavaky
1. Interval aktualizace je možné konfigurovat v properties souboru.
2. Aplikaci je možné spustit v only-read modu. To deaktivuje jak MVC tak REST metody, které dělají něco jiného než čtou. Zároveň to i deaktivuje mazání starých záznamů a stahování nových z OpenWeather. Proto je zde tolik controllerů protože Spring umí zablokovat pouze celý kontroller a jelikož mám pro každou věc jakoby dva controllery (jeden pro MVC a druhý pro REST) je jich tam tolik. Jak MVC a REST se pak tedy dělí ještě na další dva vždy podle toho zda jen čtou, nebo i pracují jinak s daty, aby bylo možné zablokovat celý kontroller.
3. Konfigurace expirace záznamů je taky možná. To jsem vymyslel tak že vždy o půlnoci se spustí metoda která smaže všechny záznamy starší než zadaný počet dní. Zadává se v celých počtech dní.

## Java
Program byl dělaný pro Javu 17.

## Běh aplikace
Mne vše fungovalo, nechával jsem aplikaci běžet i přes noc a za celou noc nespadla. Pokud se bude někdo snažit aplikaci rozbít myslím že to půjde nicméně jsem se snažil ošetřit většinu vstupů atd.

## Maven sestavení
Jelikož používám in-memory databázi tak k ní je možné se připojit pouze jednou tzn pokud aplikace běží např. v InteliJ není možné prověst Maven sestavení protože se nemůže pčipojit k db. Všechny test projdou.

## Proč je zde MVC
První jsem udělal MVC nějak jsem nad tím nepřemýšlel a až pak mi došlo že to má mít REST API tak jsem ho dodělal trochu na sílu.