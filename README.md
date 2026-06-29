# 🚀 EcoMM

> A full-stack E-Commerce platform built using Spring Boot and React.

**EcoMM** is more than a shopping website.

It was built to explore real-world backend challenges like authentication, payment processing, inventory reservation, concurrent checkout, extensible architecture, and deployment.

> The double **M** represents **Multiple users** interacting with the platform simultaneously — inspired by one of the most interesting problems tackled in this project: **handling concurrent checkout safely.**



https://github.com/user-attachments/assets/9aae212b-4283-4f42-8515-39c1a0df7758


---

## 🌐 Live Demo

Frontend:
https://ecomm-by-harshbhatter.netlify.app/

Backend:
https://myecom2.onrender.com

> ⚠️ The backend is hosted on Render's free tier.
>
> - First request may take around 2 minutes.
> - Email notifications are disabled on the hosted version because Render blocks outbound SMTP on the free tier.
---
# ✨ Features

## 🔐 Authentication

- Google OAuth2 Login
- JWT Authentication
- Role-Based Authorization
- Secure REST APIs

---

## 🛍 Shopping Experience

- Category Navigation
- Product Catalogue
- Individual Product Pages
- Shopping Cart
- Order Summary
- Order Management

---

## 🎟 Coupon Engine

Supports

- Percentage Discounts
- Flat Discounts
- Minimum Cart Validation
- Usage Limits
- Expiry Validation

Designed so new coupon types and discount rules can be added easily.

---

## 💳 Payments

- Razorpay Integration

Architecture allows new payment gateways to be integrated with minimal changes.

---

## 📧 Notifications

- Email Notifications

Notification service is designed to support different providers like SMS or Email without changing business logic.

---

## ⚡ Inventory Reservation

One of the biggest goals of this project was preventing overselling.

Implemented:

- Reservation-based checkout
- Concurrency-safe inventory updates
- Reservation expiry
- Graceful handling of simultaneous purchases

Verified using Apache JMeter.

---

# 🧪 Concurrency Testing

Concurrent checkout tested using Apache JMeter.

Scenario:

Stock = 1

Two users purchase simultaneously.

Result:

✅ One order succeeds

✅ One order fails gracefully

✅ Inventory remains consistent


https://github.com/user-attachments/assets/cac8ed97-78c7-44f6-ac32-8d889475dd6f


---

# 🛠 Tech Stack

## Frontend

- React
- CSS
- Axios
- React Router

## Backend

- Java
- Spring Boot
- Spring Security
- Hibernate
- JWT
- OAuth2

## Database

- MySQL

## Testing

- Apache JMeter

## Deployment

- Netlify
- Render
- Aiven

---
