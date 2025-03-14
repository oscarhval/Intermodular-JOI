import React, { useEffect, useState } from 'react';
import { firestore } from './firebaseConfig';
import { Bar, Pie,Line  } from 'react-chartjs-2';

import { Chart, registerables } from 'chart.js';
Chart.register(...registerables);

const Dashboard = () => {
  const [projects, setProjects] = useState([]);
  const [facturas, setFacturas] = useState([]);
  const [perfiles, setPerfiles] = useState([]);
  const [selectedProfile, setSelectedProfile] = useState('');
  

  useEffect(() => {
  
    
    const unsubscribeProjects = firestore
      .collection('projects')
      .onSnapshot(snapshot => {
        const projectsData = snapshot.docs.map(doc => ({
          id: doc.id,
          ...doc.data()
        }));
        setProjects(projectsData);
      });

      
    const unsubscribeFacturas = firestore
      .collection('facturas')
      .onSnapshot(snapshot => {
        const facturasData = snapshot.docs.map(doc => ({
          id: doc.id,
          ...doc.data()
        }));
        setFacturas(facturasData);
      });

    const unsubscribePerfiles = firestore
      .collection('perfiles')
      .onSnapshot(snapshot => {
        const perfilesData = snapshot.docs.map(doc => ({
          id: doc.id,
          ...doc.data()
        }));
        setPerfiles(perfilesData);
        if (perfilesData.length > 0 && !selectedProfile) {
          setSelectedProfile(perfilesData[0].name);
        }
      });

    return () => {
      unsubscribeProjects();
      unsubscribeFacturas();
      unsubscribePerfiles();
    };
  }, [selectedProfile]);



  

  const profileInfo = perfiles.find(perfil => perfil.name === selectedProfile);

  const chartData = {
    labels: projects.map(project => project.name),
    datasets: [
      {
        label: 'Cantidad de Tecnologías',
        data: projects.map(project => 
          project.technologies ? project.technologies.length : 0
        ),
        backgroundColor: (context) => {
          const chart = context.chart;
          const { ctx, chartArea } = chart;
          if (!chartArea) return null;
          const gradient = ctx.createLinearGradient(0, chartArea.top, 0, chartArea.bottom);
          gradient.addColorStop(0, 'rgb(100, 201, 255)');
          gradient.addColorStop(1, 'rgb(0, 34, 255)');
          return gradient;
        },
        borderColor: 'rgba(0, 0, 0, 0.6)',
        borderWidth: 2,
        hoverBackgroundColor: 'rgba(0, 34, 255,0.8)',
        hoverBorderColor: 'rgba(0, 0, 0, 0.6)',
        hoverBorderWidth: 3,
        barThickness: 20,
        barPercentage: 0.9,  
      }
    ]
  };

  const facturasByType = facturas.reduce((acc, factura) => {
    let type = factura.tipo || factura.type || 'Sin Tipo';
    if (type === 'Seleccionar tipo de factura') {
      type = 'Sin tipo';
    }
    acc[type] = (acc[type] || 0) + 1;
    return acc;
  }, {});



  const pieData = {
    labels: Object.keys(facturasByType),
    datasets: [
      {
        data: Object.values(facturasByType),
        backgroundColor: Object.keys(facturasByType).map((_, i) => {
          const colors = [
            'rgb(47, 0, 255)',
            'rgb(100, 154, 255)',
            'rgb(151, 151, 151)',
          ];
          return colors[i % colors.length];
        }),
        hoverBackgroundColor: Object.keys(facturasByType).map((_, i) => {
          const hoverColors = [
            'rgba(0, 0, 0, 0.8)'
          ];
          return hoverColors[i % hoverColors.length];
        }),
        borderColor: 'rgba(0, 0, 0, 0.6)',
        borderWidth: 2,
        hoverBorderColor: 'rgba(0, 0, 0, 0.8)',
        hoverBorderWidth: 3
      }
    ]
  };

  const totalIngresos = facturas.reduce(
    (acc, factura) => {
      if (factura.tipo === "Venta") {
        return acc + parseFloat(factura.total || 0);
      }
      return acc;
    },
    0
  );

  const totalPerdidas = facturas.reduce(
    (acc, factura) => {
      if (factura.tipo === "Compra") {
        return acc + parseFloat(factura.total || 0);
      }
      return acc;
    },
    0
  );

  const gananciaPerdidaNeta = totalIngresos - totalPerdidas;
  const gananciaPerdidaNetaColor = gananciaPerdidaNeta >= 0 ? 'green' : 'red';
  const totalFacturas = facturas.length;

  return (
    <div style={styles.container}>
      <header style={styles.header}>
        <h1 style={styles.h1}>Dashboard Intermodular JOI</h1>
      </header>

      <section style={styles.section}>
        <h2 style={styles.sectionTitle}>Perfiles</h2>
        <div style={styles.profileSelect}>
          <label htmlFor="profileSelect" style={styles.profileSelectLabel}>
            Selecciona Perfil:
          </label>
          <select
            id="profileSelect"
            value={selectedProfile}
            onChange={e => setSelectedProfile(e.target.value)}
            style={styles.profileSelectSelect}
          >
            {perfiles.map(perfil => (
              <option key={perfil.id} value={perfil.name}>
                {perfil.name}
              </option>
            ))}
          </select>
        </div>

        {profileInfo ? (
          <div style={styles.profileInfo}>
            <h3 style={styles.profileInfoH3}>{profileInfo.name}</h3>
            <p style={styles.profileInfoP}>{profileInfo.description}</p>
            <h4 style={styles.profileInfoH4}>Requisitos:</h4>
            <ul style={styles.profileInfoUl}>
              {profileInfo.requisitos &&
                profileInfo.requisitos.map((req, index) => (
                  <li key={index} style={styles.profileInfoLi}>
                    {index + 1}. {req}
                  </li>
                ))}
            </ul>

           

          </div>

        ) : (
          <p>Cargando información del perfil...</p>
        )}
      </section>

      <section style={styles.section}>
        <h2 style={styles.sectionTitle}>Proyectos Profesionales</h2>
        <div style={styles.projectsList}>
          <h3 style={styles.projectsListH3}>Listado de Proyectos</h3>
          {projects.length > 0 ? (
            <ul style={styles.projectsListUl}>
              {projects.map(project => (
                <li
                  key={project.id}
                  style={styles.projectsListLi}
                  onMouseEnter={e => {
                    e.currentTarget.style.transform = 'translateX(5px)';
                    e.currentTarget.style.boxShadow =
                      '0 4px 12px rgba(0, 0, 0, 0.1)';
                  }}
                  onMouseLeave={e => {
                    e.currentTarget.style.transform = 'translateX(0)';
                    e.currentTarget.style.boxShadow = 'none';
                  }}
                >
                  <strong style={{ color: '#1a73e8' }}>{project.name}</strong>
                  : {project.description}
                  <br />
                  <em>
                    Tecnologías:{' '}
                    {project.technologies
                      ? project.technologies.join(', ')
                      : 'N/A'}
                  </em>
                </li>
              ))}
            </ul>
          ) : (
            <p>No se encontraron proyectos.</p>
          )}
        </div>
        <div style={styles.chartContainer}>
          <h3 style={styles.projectsListH3}>
            Comparativa de Proyectos
          </h3>
          <Bar data={chartData} options={{ responsive: true }} />
        </div>
      </section>

      <section style={styles.section}>
        <h2 style={styles.sectionTitle}>Facturación</h2>

        <div style={styles.facturacion}>
          <p style={styles.facturacionP}>
            <strong style={styles.strongColor}>Nº total de Facturas:</strong>{' '}
            {totalFacturas}
            <br />
            <strong style={styles.strongColor}>Total ingresos por ventas:</strong>{' '}
            {totalIngresos.toFixed(2)} €
            <br />
            <strong style={styles.strongColor}>Total pérdidas por compras:</strong>{' '}
            {totalPerdidas.toFixed(2)} €
            <br />
            <strong style={styles.strongColor}>Ganancia - Pérdida Neta:</strong>{' '}
            <span style={{ color: gananciaPerdidaNetaColor }}>
              {gananciaPerdidaNeta >= 0
                ? `${gananciaPerdidaNeta.toFixed(2)} €`
                : `${gananciaPerdidaNeta.toFixed(2)} €`}
            </span>
          </p>
        </div>
        <div style={styles.pieChartContainer}>
          <h3 style={styles.projectsListH3}>
            Distribución de Facturas por Tipo
          </h3>
          <Pie data={pieData} options={{ responsive: true }} />
        </div>
      </section>
    </div>
  );
};

const styles = {
  container: {
    fontFamily: "'Montserrat', sans-serif",
    background: '#e8f0fe',
    minHeight: '100vh',
    color: '#202124',
    display: 'flex',
    flexDirection: 'column',
    padding: '20px',
  },
  header: {
    background: '#1a73e8',
    color: '#fff',
    padding: '50px 20px',
    textAlign: 'center',
    position: 'relative',
    borderBottomLeftRadius: '50px',
    borderBottomRightRadius: '50px',
    boxShadow: '0 4px 12px rgba(0, 0, 0, 0.2)',
    marginBottom: '40px',
  },
  h1: {
    margin: 0,
    fontSize: '2.5rem',
    fontWeight: 700,
  },
  section: {
    width: '880px',
    margin: '0 auto 40px auto',
    background: '#fff',
    borderRadius: '16px',
    padding: '30px',
    boxShadow: '0 4px 12px rgba(0, 0, 0, 0.1)',
  },
  sectionTitle: {
    marginTop: 0,
    fontSize: '1.8rem',
    marginBottom: '20px',
    color: '#1a73e8',
    borderBottom: '2px solid #1a73e8',
    display: 'inline-block',
    paddingBottom: '5px',
  },
  profileSelect: {
    display: 'flex',
    alignItems: 'center',
    marginBottom: '20px',
  },
  profileSelectLabel: {
    marginRight: '10px',
    fontSize: '1rem',
    color: '#5f6368',
  },
  profileSelectSelect: {
    padding: '8px 12px',
    borderRadius: '8px',
    border: '1px solid #ccc',
    fontSize: '1rem',
    outline: 'none',
  },
  profileInfo: {
    background: '#f1f3f4',
    padding: '20px',
    borderRadius: '12px',
    marginBottom: '20px',
  },
  profileInfoH3: {
    marginTop: 0,
    fontSize: '1.4rem',
    color: '#1a73e8',
  },
  profileInfoP: {
    color: '#5f6368',
    lineHeight: 1.6,
  },
  profileInfoH4: {
    marginBottom: '10px',
    color: '#202124',
  },
  profileInfoUl: {
    listStyle: 'none',
    margin: 0,
    padding: 0,
  },

  chartContainer: {
    background: '#fdfdfd',
    padding: '20px',
    borderRadius: '12px',
    border: '1px solid #ececec',
    boxShadow: '0 2px 6px rgba(0, 0, 0, 0.1)',
    marginTop: '20px',
  },
  
  profileInfoLi: {
    borderBottom: '1px dashed #ccc',
    padding: '5px 0',
  },
  profileProgressContainer: {
    marginTop: '20px',
    background: '#f9f9f9',
    padding: '15px',
    borderRadius: '8px',
  },
  profileProgressItem: {
    display: 'flex',
    justifyContent: 'space-between',
    alignItems: 'center',
    marginBottom: '10px',
  },
  progressBarContainer: {
    width: '80%',
    height: '8px',
    backgroundColor: '#e0e0e0',
    borderRadius: '8px',
    overflow: 'hidden',
  },
  progressBar: {
    width: '60%',
    height: '100%',
    backgroundColor: '#1a73e8',
    transition: 'width 0.5s ease',
  },
  projectsList: {
    marginBottom: '20px',
  },
  projectsListH3: {
    fontSize: '1.3rem',
    marginTop: 0,
    marginBottom: '15px',
    color: '#1a73e8',
  },
  projectsListUl: {
    listStyle: 'none',
    margin: 0,
    padding: 0,
  },
  projectsListLi: {
    background: '#fdfdfd',
    marginBottom: '10px',
    padding: '12px',
    borderRadius: '8px',
    border: '1px solid #ececec',
    transition: 'transform 0.3s ease, boxShadow 0.3s ease',
  },
  chartContainer: {
    background: '#fdfdfd',
    padding: '20px',
    borderRadius: '12px',
    border: '1px solid #ececec',
    boxShadow: '0 2px 6px rgba(0, 0, 0, 0.1)',
    marginTop: '20px',
  },
  facturacion: {
    background: '#fdfdfd',
    padding: '20px',
    borderRadius: '12px',
    border: '1px solid #ececec',
    textAlign: 'center',
    boxShadow: '0 2px 6px rgba(0, 0, 0, 0.1)',
  },
  facturacionP: {
    fontSize: '1.2rem',
    margin: 0,
  },
  strongColor: {
    color: '#1a73e8',
  },
  pieChartContainer: {
    width: '400px',
    margin: '20px auto 0 auto',
  },
};

export default Dashboard;
