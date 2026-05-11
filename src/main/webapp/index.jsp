<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>DailyExpense Manager | Professional Finance Tracking</title>
    
    <!-- Bootstrap 5 -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <!-- Google Fonts: Inter for professional look -->
    <link href="https://fonts.googleapis.com/css2?family=Inter:wght@300;400;500;600;700&display=swap" rel="stylesheet">
    <!-- Bootstrap Icons -->
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.0/font/bootstrap-icons.css">
    
    <style>
        :root {
            --primary-navy: #1e293b;
            --accent-blue: #2563eb;
            --bg-light: #f8fafc;
            --text-muted: #64748b;
        }
        
        body {
            font-family: 'Inter', sans-serif;
            background: #ffffff;
            color: var(--primary-navy);
            -webkit-font-smoothing: antialiased;
        }
        
        .navbar {
            padding: 1.5rem 0;
            background: #fff;
            border-bottom: 1px solid #e2e8f0;
        }
        
        .hero-section {
            padding: 100px 0;
            background-color: var(--bg-light);
        }
        
        .hero-title {
            font-weight: 800;
            font-size: 3.5rem;
            letter-spacing: -0.02em;
            line-height: 1.1;
            margin-bottom: 1.5rem;
        }
        
        .hero-subtitle {
            font-size: 1.25rem;
            color: var(--text-muted);
            max-width: 600px;
            margin-bottom: 2.5rem;
        }
        
        .btn-primary-prof {
            background-color: var(--accent-blue);
            border: none;
            padding: 12px 28px;
            font-weight: 600;
            border-radius: 8px;
            transition: all 0.2s ease;
        }
        
        .btn-primary-prof:hover {
            background-color: #1d4ed8;
            transform: translateY(-2px);
            box-shadow: 0 4px 12px rgba(37, 99, 235, 0.2);
        }
        
        .btn-outline-prof {
            border: 2px solid #e2e8f0;
            color: var(--primary-navy);
            padding: 12px 28px;
            font-weight: 600;
            border-radius: 8px;
            transition: all 0.2s ease;
        }
        
        .btn-outline-prof:hover {
            background-color: #f1f5f9;
            border-color: #cbd5e1;
        }
        
        .feature-card {
            border: 1px solid #e2e8f0;
            border-radius: 16px;
            padding: 2rem;
            height: 100%;
            transition: all 0.3s ease;
            background: #fff;
        }
        
        .feature-card:hover {
            border-color: var(--accent-blue);
            box-shadow: 0 10px 30px rgba(0,0,0,0.05);
        }
        
        .icon-box {
            width: 48px;
            height: 48px;
            background: #eff6ff;
            color: var(--accent-blue);
            border-radius: 12px;
            display: flex;
            align-items: center;
            justify-content: center;
            font-size: 1.5rem;
            margin-bottom: 1.5rem;
        }
        
        .badge-new {
            background-color: #dbeafe;
            color: var(--accent-blue);
            font-weight: 600;
            padding: 6px 12px;
            border-radius: 20px;
            font-size: 0.85rem;
            display: inline-block;
            margin-bottom: 1rem;
        }
    </style>
</head>
<body>

    <!-- Navigation -->
    <nav class="navbar navbar-expand-lg">
        <div class="container">
            <a class="navbar-brand fw-bold fs-4" href="#">
                <i class="bi bi-graph-up-arrow text-primary me-2"></i>DailyExpense
            </a>
            <div class="ms-auto">
                <a href="${pageContext.request.contextPath}/login.jsp" class="btn btn-link text-decoration-none text-dark fw-medium me-3">Sign In</a>
                <a href="${pageContext.request.contextPath}/register.jsp" class="btn btn-primary-prof btn-primary">Create Free Account</a>
            </div>
        </div>
    </nav>

    <!-- Hero Section -->
    <section class="hero-section">
        <div class="container text-center">
            <h1 class="hero-title">Daily Expense Manager</h1>
            <p class="hero-subtitle mx-auto">
                Simple, secure, and professional way to track your money and manage budgets.
            </p>
            <div class="d-flex gap-3 justify-content-center">
                <a href="${pageContext.request.contextPath}/login.jsp" class="btn btn-primary-prof btn-primary btn-lg">Login</a>
                <a href="${pageContext.request.contextPath}/register.jsp" class="btn btn-outline-prof btn-lg">Register</a>
            </div>
        </div>
    </section>

    <!-- Features Section -->
    <section id="features" class="py-5 my-5">
        <div class="container">
            <div class="text-center mb-5">
                <h2 class="fw-bold display-6">Designed for clarity</h2>
                <p class="text-muted">Everything you need to stay on top of your money.</p>
            </div>
            <div class="row g-4">
                <div class="col-md-4">
                    <div class="feature-card">
                        <div class="icon-box"><i class="bi bi-pie-chart-fill"></i></div>
                        <h4 class="fw-bold">Smart Visualization</h4>
                        <p class="text-muted">Understand your spending habits with clear, interactive charts powered by Chart.js.</p>
                    </div>
                </div>
                <div class="col-md-4">
                    <div class="feature-card">
                        <div class="icon-box"><i class="bi bi-file-earmark-pdf-fill"></i></div>
                        <h4 class="fw-bold">Professional Exports</h4>
                        <p class="text-muted">Generate formatted PDF and CSV reports for your monthly reviews or tax records.</p>
                    </div>
                </div>
                <div class="col-md-4">
                    <div class="feature-card">
                        <div class="icon-box"><i class="bi bi-bell-fill"></i></div>
                        <h4 class="fw-bold">Budget Alerts</h4>
                        <p class="text-muted">Set monthly limits and receive instant notifications when you're approaching your budget cap.</p>
                    </div>
                </div>
            </div>
        </div>
    </section>

    <footer class="py-5 border-top">
        <div class="container">
            <div class="row">
                <div class="col-md-6">
                    <h5 class="fw-bold mb-3">DailyExpense Manager</h5>
                    <p class="text-muted small">The most reliable tool for personal financial management. Built with Java and Hibernate.</p>
                </div>
                <div class="col-md-6 text-md-end">
                    <p class="text-muted small">&copy; 2026 DailyExpense. All rights reserved.</p>
                </div>
            </div>
        </div>
    </footer>

    <!-- Bootstrap JS -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
