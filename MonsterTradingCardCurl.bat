@echo off
curl -X POST http://localhost:10001/sessions --header "Content-Type: application/json" -d "{\"Username\":\"kienboec\", \"Password\":\"daniel\"}"
echo.
curl -X POST http://localhost:10001/sessions --header "Content-Type: application/json" -d "{\"Username\":\"altenhof\", \"Password\":\"markus\"}"
echo.


curl -X POST http://localhost:10001/cards --header "Content-Type: application/json" --header "Authorization: Basic admin-mtcgToken" -d "{\"Name\":\"WaterGoblin\", \"Damage\": 5.0, \"Element\":\"WATER\", \"Rarity\": \"RARE\", \"Monster Type\": \"GOBLIN\"}"
echo.
curl -X POST http://localhost:10001/cards --header "Content-Type: application/json" --header "Authorization: Basic admin-mtcgToken" -d "{\"Name\":\"GrassGoblin\", \"Damage\": 15.0, \"Element\":\"GRASS\", \"Rarity\": \"LEGENDARY\", \"Monster Type\": \"GOBLIN\"}"
echo.
curl -X POST http://localhost:10001/cards --header "Content-Type: application/json" --header "Authorization: Basic admin-mtcgToken" -d "{\"Name\":\"GrassGoblin\", \"Damage\": 4.0, \"Element\":\"GRASS\", \"Rarity\": \"COMMON\", \"Monster Type\": \"GOBLIN\"}"
echo.
curl -X POST http://localhost:10001/cards --header "Content-Type: application/json" --header "Authorization: Basic admin-mtcgToken" -d "{\"Name\":\"FireSpell\", \"Damage\": 10.0, \"Element\":\"FIRE\", \"Rarity\": \"ULTRA_RARE\"}"
echo.
curl -X POST http://localhost:10001/cards --header "Content-Type: application/json" --header "Authorization: Basic admin-mtcgToken" -d "{\"Name\":\"WaterSpell\", \"Damage\": 11.0, \"Element\":\"WATER\", \"Rarity\": \"LEGENDARY\"}"
echo.
curl -X POST http://localhost:10001/cards --header "Content-Type: application/json" --header "Authorization: Basic admin-mtcgToken" -d "{\"Name\":\"GrassSpell\", \"Damage\": 2.0, \"Element\":\"GRASS\", \"Rarity\": \"COMMON\"}"
echo.
curl -X POST http://localhost:10001/cards --header "Content-Type: application/json" --header "Authorization: Basic admin-mtcgToken" -d "{\"Name\":\"FireSpell\", \"Damage\": 4.0, \"Element\":\"GRASS\", \"Rarity\": \"RARE\"}"

REM this is approx a sleep 
ping localhost -n 100 >NUL 2>NUL
@echo on