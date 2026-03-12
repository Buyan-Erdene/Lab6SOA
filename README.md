Суулгах заавар
Шаардлага : 
- JDK 21 (эсвэл 17)
- Eclipse IDE + Spring Tools 4
- Maven 3.8+
- Postman (туршилтад)

1. SOAP Service эхлүүлэх. user-soap-service run хийх
Шалгах: http://localhost:8081/ws/users.wsdl
2. JSON Service эхлүүлэх. user-json-service run хийх
Шалгах: http://localhost:8082/users (401 буцвал зөв)
3. Frontend нээх
frontend-app/index.html файлыг browser-т шууд нээнэ.

Өгөгдлийн сангийн сонголт : 
Сонгосон: Shared H2 In-Memory Database
Шалтгаан:
Хурдан, Суулгах шаардлагагүй, Хоёр сервис нэг DB ашиглах тул token validation шууд ажилладаг
Сул тал : Програм унтрахад өгөгдөл устдаг 


Архитектурын шийдвэрүүд
1. Authentication delegation : 
JSON Service нь authentication шууд хийхгүй — SOAP ValidateToken руу middleware-ээр дамжуулна. Энэ нь Single Responsibility Principle-ийг баримтална.
2. Token стратеги :
UUID-д суурилсан энгийн token ашигласан. Login хийх бүрт шинэ token үүсч, DB-д хадгалагдана. 