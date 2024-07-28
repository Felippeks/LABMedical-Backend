const { defineConfig } = require('cypress');
const tokens = require('./cypress/support/tokens');

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
        }
      });
    },
    specPattern: 'cypress/e2e/Pacientes/*.cy.js'
  }
});
