const { defineConfig } = require('cypress');
const tokens = require('./cypress/support/tokens');
const { Client } = require('pg');

let patientData = {};
let patientId = {};
let medicoData = {};
let medicoId = {};
let adminData = {};
let adminId = {};

module.exports = defineConfig({
  e2e: {
    setupNodeEvents(on, config) {
      on('task', {
        setToken({ role, token }) {
          tokens.setToken(role, token);
          return null;
        },
        getToken(role) {
          const token = tokens.getToken(role);
          if (token) {
            return token;
          } else {
            throw new Error(`Token for role ${role} not found`);
          }
        },
        setPatientData(data) {
          patientData = data;
          return null;
        },
        getPatientData() {
          return patientData;
        },
        savePatientId(id) {
          patientId = id;
          console.log(`Saved patient ID: ${patientId}`);
          return null;
        },
        getPatientId() {
          console.log(`Retrieved patient ID: ${patientId}`);
          return patientId;
        },
        setMedicoData(data) {
          medicoData = data;
          return null;
        },
        getMedicoData() {
          return medicoData;
        },
        saveMedicoId(id) {
          medicoId = id;
          console.log(`Saved medico ID: ${medicoId}`);
          return null;
        },
        getMedicoId() {
          console.log(`Retrieved medico ID: ${medicoId}`);
          return medicoId;
        },
        setAdminData(data) {
          adminData = data;
          return null;
        },
        getAdminData() {
          return adminData;
        },
        saveAdminId(id) {
          adminId = id;
          console.log(`Saved admin ID: ${adminId}`);
          return null;
        },
        getAdminId() {
          console.log(`Retrieved admin ID: ${adminId}`);
          return adminId;
        },
        async queryDatabase(query) {
          const client = new Client({
            host: 'localhost',
            user: 'admin',
            password: '1q2w3E@!',
            database: 'LabMedical',
            port: '5455'
          });

          try {
            await client.connect();
            const res = await client.query(query);
            return res.rows;
          } catch (error) {
            console.error('Database query error:', error);
            throw error;
          } finally {
            await client.end();
          }
        }
      });
    },
    specPattern: 'cypress/e2e/{00_Login,01_Cadastro,02_Paciente,03_Consulta}/*.cy.js'
  }
});
