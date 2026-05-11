<%@ page contentType="text/html;charset=UTF-8" %>

<!DOCTYPE html>
<html>
<head>
    <title>Register | DailyExpense</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://fonts.googleapis.com/css2?family=Inter:wght@400;600;700&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.0/font/bootstrap-icons.css">
    <script src="https://accounts.google.com/gsi/client" async defer></script>
    <style>
        body { font-family: 'Inter', sans-serif; background: #f8fafc; color: #1e293b; }
        .card { border-radius: 16px; border: 1px solid #e2e8f0; box-shadow: 0 4px 6px -1px rgba(0, 0, 0, 0.1); }
        .btn-primary { background: #2563eb; border: none; padding: 12px; font-weight: 600; border-radius: 8px; }
        .btn-primary:hover { background: #1d4ed8; }
    </style>
</head>
<body>
<div class="container">
    <div class="row justify-content-center align-items-center vh-100">
        <div class="col-md-5">
            <div class="card p-4">
                <div class="card-body">
                    <h3 class="text-center mb-4 fw-bold">Create Account</h3>
                    
                    <% String error = (String) request.getAttribute("error"); if (error != null) { %>
                    <div class="alert alert-danger py-2 small"><%= error %></div>
                    <% } %>

                    <form action="register" method="post">
                        <div class="mb-3">
                            <label class="form-label small fw-bold">Full Name</label>
                            <input type="text" name="username" class="form-control" required placeholder="Tirth">
                        </div>
                        <div class="mb-3">
                            <label class="form-label small fw-bold">Email Address</label>
                            <input type="email" name="email" class="form-control" required placeholder="name@company.com">
                        </div>
                        <div class="mb-3">
                            <label class="form-label small fw-bold">Password</label>
                            <input type="password" name="password" class="form-control" required placeholder="Min. 6 characters">
                        </div>
                        <button type="submit" class="btn btn-primary w-100">Sign Up</button>
                    </form>

                    <div class="text-center my-3 text-muted small">OR</div>

                    <div class="d-flex justify-content-center">
                        <div id="g_id_onload"
                             data-client_id="145131493824-v5ticedsn6ddqjue97t7ss8nnnn0g3jb.apps.googleusercontent.com"
                             data-context="signup"
                             data-ux_mode="popup"
                             data-callback="handleCredentialResponse"
                             data-auto_prompt="false">
                        </div>
                        <div class="g_id_signin" data-type="standard" data-shape="rectangular" data-theme="outline" data-text="signup_with" data-size="large" data-logo_alignment="left"></div>
                    </div>

                    <div class="text-center mt-4 small text-muted">
                        Already have an account? <a href="login.jsp" class="text-decoration-none fw-bold">Login</a>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<script>
    function handleCredentialResponse(response) {
        const xhr = new XMLHttpRequest();
        xhr.open('POST', '${pageContext.request.contextPath}/googleLogin');
        xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
        xhr.onload = function() {
            if (xhr.responseText === 'success') {
                window.location.href = '${pageContext.request.contextPath}/viewExpenses';
            } else {
                alert('Google Sign-In failed: ' + xhr.responseText);
            }
        };
        xhr.send('idtoken=' + response.credential);
    }
</script>
</body>
</html>
