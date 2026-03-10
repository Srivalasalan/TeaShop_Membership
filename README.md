# 🍵 Tea Shop Membership System

A backend REST API for managing tea shop memberships built with Spring Boot and PostgreSQL.

---

## Tech Stack

- Java 17
- Spring Boot
- Spring Data JPA / Hibernate
- PostgreSQL
- BCrypt (PIN hashing)
- Maven

---

## Architecture

```
Controller → Service → DAO → Repository → PostgreSQL
```

### Package Structure
```
com.teashop.Membership
├── controller
├── service
├── dao
├── repository
├── entity
├── dto
│   ├── request
│   └── response
├── exception
└── util
```

---

## Database Schema

```
admin              → id, userName, password
member             → id, name, phone, pin
subscription_plan  → id, planName, totalTeas, amount, durationDays
subscription       → id, member_id, plan_id, startDate, endDate, teasUsed, status
otp                → id, phone, otpCode, generatedAt, isUsed
```

---

## API Endpoints

### Admin
| Method | Endpoint | Description |
|---|---|---|
| POST | `/Tmembership/Admin` | Add admin |
| POST | `/Tmembership/Admin/login` | Admin login |
| GET | `/Tmembership/Admin` | Get all admins (paginated) |

### Member
| Method | Endpoint | Description |
|---|---|---|
| POST | `/Tmembership/Member` | Add member |
| GET | `/Tmembership/Member` | Get all members (paginated) |
| DELETE | `/Tmembership/Member` | Delete member by phone |
| POST | `/Tmembership/Member/login` | Member login |
| GET | `/Tmembership/Member/profile` | View profile |
| PUT | `/Tmembership/Member/update-name` | Update name |
| POST | `/Tmembership/Member/request-otp` | Request OTP for PIN change |
| PUT | `/Tmembership/Member/change-pin` | Change PIN with OTP |

### Subscription Plan
| Method | Endpoint | Description |
|---|---|---|
| POST | `/Tmembership/SubPlan` | Add plan |
| GET | `/Tmembership/SubPlan` | Get all plans (paginated) |
| PUT | `/Tmembership/SubPlan/{id}` | Update plan |
| DELETE | `/Tmembership/SubPlan/{id}` | Delete plan |

### Subscription
| Method | Endpoint | Description |
|---|---|---|
| POST | `/Tmembership/Subscription` | Assign plan to member |
| PUT | `/Tmembership/Subscription/use-tea` | Mark tea as used |
| GET | `/Tmembership/Subscription/active` | Get active subscription |
| GET | `/Tmembership/Subscription/history` | Get subscription history |

---

## Setup

**1. Create PostgreSQL database**
```sql
CREATE DATABASE teashop_membership;
```

**2. Configure `application.properties`**
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/teashop_membership
spring.datasource.username=your_username
spring.datasource.password=your_password
spring.jpa.hibernate.ddl-auto=update
```



**3. Test endpoints using Postman**

---

## Security

- PINs and passwords hashed using **BCrypt**
- OTP-based PIN change with **5-minute expiry**
- OTP is **single-use only**

---

## Note on OTP

Currently uses Mock OTP (prints to console). Can be replaced with real SMS by integrating **Twilio** or **Fast2SMS** in `OtpService.java`.

---

## Author

**Srivalasalan M S**
Java Backend Developer | Chennai, India
srivalasalan.dev@gmail.com
