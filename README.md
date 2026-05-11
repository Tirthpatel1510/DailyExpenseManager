# 🚀 Daily Expense Manager

A professional, full-stack Java web application for managing personal finances. Built with modern security, dynamic analytics, and a premium user interface.

## ✨ Key Features

- **🔐 Secure Authentication**: Multi-user system with BCrypt password hashing.
- **📊 Dynamic Analytics**: Interactive Pie and Bar charts (Chart.js) for category breakdown and monthly trends.
- **💰 Budget Management**: Set monthly budget limits with real-time overspending alerts.
- **🔍 Advanced Filtering**: Search by title/category and server-side pagination for large datasets.
- **📥 Data Export**: Download expense reports in **PDF** (formatted) and **CSV** formats.
- **📱 Responsive UI**: Premium design built with Bootstrap 5, Glassmorphism, and Google Fonts.
- **🏗️ Solid Architecture**: Built using MVC pattern with a dedicated Service Layer and Hibernate ORM.

## 🛠️ Technology Stack

- **Backend**: Java, Servlet, Jakarta Servlet API
- **ORM**: Hibernate 5.6
- **Database**: MySQL 8.0
- **Frontend**: JSP, Bootstrap 5, Chart.js, Bootstrap Icons
- **Security**: jBCrypt 0.4
- **Reporting**: iText PDF, Apache Commons CSV
- **Build Tool**: Maven
- **CI/CD**: GitHub Actions

## 🚀 Getting Started

### Prerequisites
- JDK 17+
- Apache Tomcat 10+
- MySQL Server 8.0+
- Maven 3.6+

### Setup
1. **Clone the repository**:
   ```bash
   git clone https://github.com/your-username/DailyExpenseManager.git
   ```
2. **Configure Database**:
   - Create a database named `expense_manager`.
   - Update `src/main/resources/hibernate.cfg.xml` (if present) or `HibernateUtil.java` with your MySQL credentials.
3. **Build the project**:
   ```bash
   mvn clean package
   ```
4. **Deploy**:
   - Copy the generated `DailyExpenseManager.war` from `target/` to your Tomcat `webapps/` folder.
   - Start Tomcat and access the app at `http://localhost:8080/DailyExpenseManager`.

## 🧪 Running Tests
```bash
mvn test
```

## 📈 Roadmap
- [ ] Google OAuth Integration (Backend Callback)
- [ ] Budget Overspending Email Notifications
- [ ] Mobile App API (RESTful Service Layer)
- [ ] Dark Mode Persistence

## 📄 License
Distributed under the MIT License. See `LICENSE` for more information.

