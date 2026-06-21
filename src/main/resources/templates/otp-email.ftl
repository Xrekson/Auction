<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Verify Your Email</title>
    <style>
        body {
            font-family: 'Outfit', 'Inter', -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, sans-serif;
            background-color: #0f172a;
            color: #f8fafc;
            margin: 0;
            padding: 0;
            -webkit-font-smoothing: antialiased;
            -moz-osx-font-smoothing: grayscale;
        }
        .container {
            max-width: 600px;
            margin: 40px auto;
            padding: 20px;
        }
        .card {
            background-color: #1e293b;
            border: 1px solid #334155;
            border-radius: 16px;
            padding: 40px;
            text-align: center;
            box-shadow: 0 10px 25px -5px rgba(0, 0, 0, 0.3), 0 8px 10px -6px rgba(0, 0, 0, 0.3);
        }
        .logo-text {
            font-size: 24px;
            font-weight: 800;
            background: linear-gradient(135deg, #6366f1 0%, #a855f7 100%);
            -webkit-background-clip: text;
            -webkit-text-fill-color: transparent;
            margin-bottom: 24px;
            letter-spacing: 0.05em;
        }
        h1 {
            font-size: 22px;
            color: #ffffff;
            margin-top: 0;
            margin-bottom: 16px;
            font-weight: 700;
        }
        p {
            color: #94a3b8;
            font-size: 16px;
            line-height: 1.6;
            margin-top: 0;
            margin-bottom: 24px;
        }
        .otp-container {
            background: rgba(99, 102, 241, 0.08);
            border: 2px dashed #6366f1;
            border-radius: 12px;
            padding: 20px;
            margin: 32px 0;
        }
        .otp-code {
            font-size: 36px;
            font-weight: 800;
            letter-spacing: 8px;
            color: #818cf8;
            margin: 0;
        }
        .expiry-text {
            font-size: 14px;
            color: #f43f5e;
            margin-top: 12px;
            font-weight: 600;
        }
        .footer {
            margin-top: 40px;
            border-top: 1px solid #334155;
            padding-top: 20px;
            font-size: 12px;
            color: #64748b;
        }
        .footer a {
            color: #6366f1;
            text-decoration: none;
        }
        .footer a:hover {
            text-decoration: underline;
        }
    </style>
</head>
<body>
    <div class="container">
        <div class="card">
            <div class="logo-text">VENDUE AUCTIONS</div>
            <h1>Verify your registration</h1>
            <p>Hi ${name},</p>
            <p>Thank you for signing up for Vendue! Please use the following One-Time Password (OTP) to verify your email address and activate your account:</p>
            
            <div class="otp-container">
                <div class="otp-code">${otp}</div>
                <div class="expiry-text">This code will expire in 5 minutes.</div>
            </div>
            
            <p>If you did not request this, you can safely ignore this email.</p>
            
            <div class="footer">
                <p>This is an automated message, please do not reply directly.</p>
                <p>&copy; 2026 Vendue. All rights reserved.</p>
            </div>
        </div>
    </div>
</body>
</html>
