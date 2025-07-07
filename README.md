# JavaBack

Spring Boot + JPA로 구축된 백엔드 API 프로젝트입니다.

## 🚀 기술 스택

- **Java 17**
- **Spring Boot 3.2.0**
- **Spring Data JPA**
- **Spring Security**
- **MySQL**
- **JWT (JSON Web Token)**
- **Swagger/OpenAPI 3**
- **Gradle**
- **Lombok**

## 📋 주요 기능

### 인증 (Authentication)
- ✅ 회원가입
- ✅ 로그인
- ✅ JWT 토큰 기반 인증
- ✅ 비밀번호 암호화 (BCrypt)

### 사용자 관리 (User Management)
- ✅ 사용자 프로필 조회
- ✅ 모든 사용자 목록 조회
- ✅ 특정 사용자 조회
- ✅ 사용자 정보 수정
- ✅ 사용자 삭제
- ✅ 사용자 활성화/비활성화

### API 문서화
- ✅ Swagger UI 자동 생성
- ✅ OpenAPI 3.0 스펙
- ✅ API 테스트 인터페이스

### 보안
- ✅ Spring Security 설정
- ✅ JWT 인증 필터
- ✅ CORS 설정
- ✅ 입력값 검증 (Validation)

## 🛠️ 설치 및 실행

### 필수 요구사항
- Java 17 이상
- MySQL 8.0 이상
- Gradle (선택사항 - Gradle Wrapper 포함)

### 1. 데이터베이스 설정
```sql
CREATE DATABASE javaback;
```

### 2. 환경 변수 설정
`.env` 파일을 생성하거나 환경 변수를 설정하세요:

```bash
DB_USER=root
DB_PASSWORD=your_password
JWT_SECRET=your-secret-key-here-make-it-long-and-secure
```

### 3. 프로젝트 실행

#### IntelliJ IDEA에서 실행
1. 프로젝트를 IntelliJ에서 열기
2. `JavaBackApplication.java` 실행
3. 또는 Gradle 탭에서 `bootRun` 실행

#### 터미널에서 실행
```bash
# Windows
.\gradlew.bat bootRun

# Linux/Mac
./gradlew bootRun
```

### 4. 서버 접속
- **서버**: http://localhost:8080
- **Swagger UI**: http://localhost:8080/swagger-ui.html
- **API 문서**: http://localhost:8080/api-docs

## 📚 API 엔드포인트

### 인증 API
| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/auth/register` | 회원가입 |
| POST | `/auth/login` | 로그인 |

### 사용자 API (인증 필요)
| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/users/profile` | 내 프로필 조회 |
| GET | `/users` | 모든 사용자 조회 |
| GET | `/users/{id}` | 특정 사용자 조회 |
| PATCH | `/users/{id}` | 사용자 정보 수정 |
| DELETE | `/users/{id}` | 사용자 삭제 |

## 🔐 인증 방식

### JWT 토큰 사용
1. 로그인 후 JWT 토큰을 받습니다
2. 이후 요청 시 Authorization 헤더에 토큰을 포함합니다:
```
Authorization: Bearer <your-jwt-token>
```

## 📁 프로젝트 구조

```
src/main/java/com/example/javaback/
├── JavaBackApplication.java          # 메인 애플리케이션
├── config/                           # 설정 클래스들
│   ├── SecurityConfig.java          # 보안 설정
│   ├── JwtAuthenticationFilter.java # JWT 인증 필터
│   └── SwaggerConfig.java           # Swagger 설정
├── controller/                       # 컨트롤러
│   ├── AuthController.java          # 인증 컨트롤러
│   └── UserController.java          # 사용자 컨트롤러
├── dto/                             # 데이터 전송 객체
│   ├── ApiResponse.java             # 공통 응답 형식
│   ├── LoginRequest.java            # 로그인 요청
│   └── RegisterRequest.java         # 회원가입 요청
├── entity/                          # JPA 엔티티
│   └── User.java                    # 사용자 엔티티
├── exception/                       # 예외 처리
│   └── GlobalExceptionHandler.java  # 글로벌 예외 처리
├── repository/                      # 데이터 접근 계층
│   └── UserRepository.java          # 사용자 리포지토리
├── service/                         # 비즈니스 로직
│   ├── AuthService.java             # 인증 서비스
│   └── UserService.java             # 사용자 서비스
└── util/                           # 유틸리티
    └── JwtUtil.java                # JWT 유틸리티
```

## 🧪 테스트

### API 테스트
Swagger UI를 통해 API를 테스트할 수 있습니다:
1. http://localhost:8080/swagger-ui.html 접속
2. 원하는 API 엔드포인트 선택
3. "Try it out" 버튼 클릭
4. 파라미터 입력 후 "Execute" 실행

### 예시 요청

#### 회원가입
```bash
curl -X POST "http://localhost:8080/auth/register" \
  -H "Content-Type: application/json" \
  -d '{
    "name": "홍길동",
    "email": "hong@example.com",
    "password": "password123"
  }'
```

#### 로그인
```bash
curl -X POST "http://localhost:8080/auth/login" \
  -H "Content-Type: application/json" \
  -d '{
    "email": "hong@example.com",
    "password": "password123"
  }'
```

## 🔧 개발 환경 설정

### IntelliJ IDEA 설정
1. **Lombok 플러그인 설치**
2. **Annotation Processing 활성화**
   - Settings → Build, Execution, Deployment → Compiler → Annotation Processors
   - "Enable annotation processing" 체크

### 데이터베이스 연결
- **Host**: localhost
- **Port**: 3306
- **Database**: javaback
- **Username**: root (또는 설정한 사용자)
- **Password**: 설정한 비밀번호

## 📝 라이선스

이 프로젝트는 MIT 라이선스 하에 배포됩니다.

## 🤝 기여하기

1. Fork the Project
2. Create your Feature Branch (`git checkout -b feature/AmazingFeature`)
3. Commit your Changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the Branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## 📞 문의

프로젝트에 대한 문의사항이 있으시면 이슈를 생성해주세요. 