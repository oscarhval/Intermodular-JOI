import React, { useState } from 'react';
import { auth, googleProvider } from './firebase'; // Asegúrate de que tu archivo firebase.js esté configurado correctamente.
import { createUserWithEmailAndPassword, signInWithPopup, signOut } from 'firebase/auth';
import { useNavigate } from 'react-router-dom'; // Importa useNavigate si estás usando React Router.

function Register() {
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [confirmPassword, setConfirmPassword] = useState('');
  const [errorMessage, setErrorMessage] = useState('');
  const navigate = useNavigate(); // Usa useNavigate para redirigir con React Router.

  // Manejo del registro con correo electrónico y contraseña
  const handleRegister = async (event) => {
    event.preventDefault();

    if (password !== confirmPassword) {
      setErrorMessage("Las contraseñas no coinciden");
      return;
    }

    try {
      const res = await createUserWithEmailAndPassword(auth, email, password);
      await signOut(auth); // Cierra la sesión para que el usuario no quede logueado automáticamente
      alert("Registro exitoso, por favor inicie sesión");
      navigate('/'); // Redirige a la página de login
    } catch (error) {
      setErrorMessage(error.message);
    }
  };

  // Manejo del registro con Google
  const handleGoogleSignUp = async () => {
    try {
      const res = await signInWithPopup(auth, googleProvider);
      console.log("Registro/Inicio con Google exitoso:", res.user);
      navigate('/dashboard'); // Redirige al Dashboard si es necesario
    } catch (error) {
      setErrorMessage(error.message);
    }
  };

  return (
    <main className="register-container">
      {/* Botón para redirigir a la página de Login */}
      <button 
        className="back-to-login-btn" 
        onClick={() => navigate('/')} // Redirige a la página de login con React Router
      >
        Iniciar sesión
      </button>
      
      <div className="register-box">
        <h2>Registrarse</h2>
        {errorMessage && <p className="error">{errorMessage}</p>}
        <form onSubmit={handleRegister}>
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
          <div className="form-group">
            <input
              type="password"
              id="confirmPassword"
              value={confirmPassword}
              onChange={(e) => setConfirmPassword(e.target.value)}
              placeholder="Confirmar contraseña"
              required
            />
          </div>
          <button type="submit" className="btn">Registrarse</button>
        </form>
        <div className="divider">o</div>
        <button className="btn google-btn" onClick={handleGoogleSignUp}>
          Continúa con
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

  .register-container {
    display: flex;
    justify-content: center;
    align-items: center;
    height: 100vh;
    background: #fff;
    animation: fadeIn 1s ease-out;
    position: relative; /* Necesario para el posicionamiento del botón */
  }

  /* Estilo del botón "Iniciar sesión" */
  .back-to-login-btn {
    position: absolute;
    top: 20px;
    left: 20px;
    background-color: #1a73e8; /* Azul */
    color: white;
    padding: 10px 15px;
    border: none;
    border-radius: 5px;
    cursor: pointer;
    font-size: 0.9rem;
    transition: background-color 0.3s ease;
  }

  .back-to-login-btn:hover {
    background-color: #1669c1; /* Azul más oscuro */
  }

  .register-box {
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
    color: #1a73e8; /* Azul */
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
        border-color: #1a73e8; /* Azul */
        box-shadow: 0 0 0 3px rgba(26, 115, 232, 0.2);
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
    background-color: #1a73e8; /* Azul */
    color: #fff;
    width: 100%;

    &:hover {
      background-color: #1669c1; /* Azul más oscuro */
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

export default Register;
