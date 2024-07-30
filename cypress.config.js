const { defineConfig } = require('cypress');
const tokens = require('./cypress/support/tokens');
const { Client } = require('pg');

let patientData = {};
let patientId = {};

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
        async queryDatabase(query) {
          const client = new Client({
            host: 'localhost',
            user: 'admin',
            password: '1q2w3E@!',
            database: 'LabMedical',
            port: '5455'

          });

          await client.connect();
          const res = await client.query(query);
          await client.end();
          return res.rows;
        }
      });
    },
    specPattern: 'cypress/e2e/{00_Login,01_Cadastro,02_Pacientes}/*.cy.js'
  }
});