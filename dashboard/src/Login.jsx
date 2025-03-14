import React, { useState } from 'react';
import { auth, googleProvider } from './firebase'; // Asegúrate de que tu archivo firebase.js esté configurado correctamente.
import { signInWithEmailAndPassword, signInWithPopup } from "firebase/auth";
import { useNavigate } from 'react-router-dom';  // Importa useNavigate para redirigir con React Router.

function Login() {
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [errorMessage, setErrorMessage] = useState('');
  const navigate = useNavigate(); // Usa useNavigate para redirigir con React Router.

  // Manejo del login con correo electrónico y contraseña
  const handleLogin = async (event) => {
    event.preventDefault();
    try {
      const res = await signInWithEmailAndPassword(auth, email, password);
      console.log("Login exitoso:", res.user);
      // Redirigir a /dashboard con React Router
      navigate('/dashboard');
    } catch (error) {
      setErrorMessage(error.message);
    }
  };

  // Manejo del login con Google
  const handleGoogleLogin = async () => {
    try {
      const res = await signInWithPopup(auth, googleProvider);
      console.log("Login con Google exitoso:", res.user);
      // Redirigir a /dashboard con React Router
      navigate('/dashboard');
    } catch (error) {
      setErrorMessage(error.message);
    }
  };

  return (
    <main className="login-container">
      <button 
        className="back-to-register-btn" 
        onClick={() => navigate('/register')} // Redirige a la página de registro con React Router
      >
        Registrarse
      </button>
      
      <div className="login-box">
        <h2>Iniciar sesión</h2>
        {errorMessage && <p className="error">{errorMessage}</p>}
        <form onSubmit={handleLogin}>
          <div className="form-group">
            <input
              type="email"
              id="email"
              value={email}
              onChange={(e) => setEmail(e.target.value)}
              placeholder="Correo electrónico"
              required
            />
          </div>
          <div className="form-group">
            <input
              type="password"
              id="password"
              value={password}
              onChange={(e) => setPassword(e.target.value)}
              placeholder="Contraseña"
              required
            />
          </div>
          <button type="submit" className="btn">Entrar</button>
        </form>
        <div className="divider">o</div>
        <button className="btn google-btn" onClick={handleGoogleLogin}>
          Inicia sesión con
          <img src="/img/Google_2015_logo.png" alt="Google logo" style={{ marginTop: '5px', height: '30px', marginLeft: '0.5rem' }} />
        </button>
      </div>
    </main>
  );
}

// Estilos CSS en línea en JavaScript
const styles = `
  @keyframes fadeIn {
    from { opacity: 0; transform: translateY(20px); }
    to { opacity: 1; transform: translateY(0); }
  }

  .login-container {
    display: flex;
    justify-content: center;
    align-items: center;
    height: 100vh; /* Cambiado a 100vh para cubrir toda la pantalla */
    background: #fff; /* Fondo blanco */
    animation: fadeIn 1s ease-out;
    position: relative;
  }

  .back-to-register-btn {
    position: absolute;
    top: 20px;
    left: 20px;
    background-color: #1a73e8; /* Azul especificado */
    color: white;
    padding: 10px 15px;
    border: none;
    border-radius: 5px;
    cursor: pointer;
    font-size: 0.9rem;
    transition: background-color 0.3s ease;
  }

  .back-to-register-btn:hover {
    background-color: #1558b1; /* Un azul más oscuro para el hover */
  }

  .login-box {
    background: #fff;
    border: 1px solid #e0e0e0;
    padding: 2rem;
    border-radius: 8px;
    box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
    width: 100%;
    max-width: 400px;
    text-align: center;
    animation: fadeIn 0.8s ease-out;
  }

  h2 {
    font-size: 1.75rem;
    padding-bottom: 1.5rem;
    color: #1a73e8; /* Azul especificado */
  }

  .error {
    background: #ffebee;
    color: #c62828;
    padding: 0.75rem;
    border-radius: 4px;
    margin-bottom: 1rem;
    animation: fadeIn 0.5s ease-out;
  }

  form {
    display: flex;
    flex-direction: column;
    gap: 1rem;
  }

  .form-group {
    text-align: left;

    label {
      display: block;
      margin-bottom: 0.5rem;
      font-size: 0.9rem;
      color: #555;
    }

    input {
      width: 100%;
      padding: 0.75rem;
      border: 1px solid #ccc;
      border-radius: 4px;
      font-size: 1rem;
      transition: border-color 0.3s ease, box-shadow 0.3s ease;

      &:focus {
        border-color: #1a73e8; /* Azul especificado */
        box-shadow: 0 0 0 3px rgba(26, 115, 232, 0.2); /* Azul especificado */
        outline: none;
      }
    }
  }

  .btn {
    padding: 0.75rem;
    border: none;
    border-radius: 4px;
    font-size: 1rem;
    cursor: pointer;
    transition: background-color 0.3s ease, transform 0.2s ease;
    background-color: #1a73e8; /* Azul especificado */
    color: #fff;
    width: 100%;

    &:hover {
      background-color: #1558b1; /* Un azul más oscuro para el hover */
      transform: translateY(-2px);
    }
  }

  .google-btn {
    background-color: #fff;
    border: 1px solid #ccc;
    color: #000;
    display: flex;
    align-items: center;
    justify-content: center;

    &:hover {
      background-color: #f9f9f9;
    }
  }

  .divider {
    margin: 1.5rem 0;
    font-size: 0.9rem;
    color: #777;
    position: relative;
    text-align: center;

    &:before, &:after {
      content: '';
      position: absolute;
      top: 50%;
      width: 40%;
      height: 1px;
      background: #ccc;
    }
    &:before { left: 0; }
    &:after { right: 0; }
  }
`;

// Crear una etiqueta <style> en el head y agregar los estilos
const styleSheet = document.createElement("style");
styleSheet.type = "text/css";
styleSheet.innerText = styles;
document.head.appendChild(styleSheet);

export default Login;
