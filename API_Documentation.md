API Documentation
SOAP Operations (port 8081)
Endpoint: POST http://localhost:8081/ws
Namespace: http://example.com/users
RegisterUser
xml<usr:RegisterUserRequest>
  <usr:username>bat</usr:username>
  <usr:password>pass123</usr:password>
  <usr:email>bat@example.com</usr:email>
</usr:RegisterUserRequest>

Response: <success>true</success><message>Бүртгэл амжилттай</message>

LoginUser
xml<usr:LoginUserRequest>
  <usr:username>bat</usr:username>
  <usr:password>pass123</usr:password>
</usr:LoginUserRequest>

Response: <success>true</success><token>uuid-token</token>

ValidateToken
xml<usr:ValidateTokenRequest>
  <usr:token>uuid-token-here</usr:token>
</usr:ValidateTokenRequest>

Response: <valid>true</valid><username>bat</username>

REST API (port 8082)
Бүх endpoint Authorization: Bearer <token> header шаардана.

POST /users — Request body:
json{
  "username": "bat",
  "name": "Bat-Erdene",
  "email": "bat@example.com",
  "bio": "Backend developer",
  "phone": "+97699001122"
}

Authentication Flow 

БҮРТГЭЛ:
  1. Frontend - POST /ws  RegisterUserRequest(username, password, email)
  2. SOAP - DB-д AuthUser хадгалах
  3. SOAP - { success: true } буцаах
  4. Frontend - Login хуудас руу шилжих

НЭВТРЭХ:
  1. Frontend - POST /ws  LoginUserRequest(username, password)
  2. SOAP - DB-д хэрэглэгч хайх, нууц үг шалгах
  3. SOAP - UUID token үүсгэж DB-д хадгалах
  4. SOAP - { success: true, token: "uuid" } буцаах
  5. Frontend - token-г localStorage-д хадгалах
  6. Frontend - /profile.html руу redirect

