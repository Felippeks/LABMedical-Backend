const { getToken } = require('../../support/tokens');

describe('Patient User Registration', () => {
    let adminToken;

    before(() => {
        cy.task('getToken', 'ADMIN').then((token) => {
            adminToken = token;
            expect(adminToken).to.not.be.undefined;
        });
    });

    it('should register a new patient using admin token', () => {
        const randomSuffix = Math.floor(Math.random() * 10000).toString().padStart(4, '0');
        const newPatient = {
            nome: `jose silva ${randomSuffix}`,
            email: `jose${randomSuffix}@gmail.com`,
            dataNascimento: '1991-04-01',
            cpf: `0666914${randomSuffix}`,
            password: '1q2w3e@!',
            perfil: 'PACIENTE'
        };

        cy.request({
            method: 'POST',
            url: 'http://localhost:8081/api/usuarios/cadastro',
            headers: {
                'Authorization': `Bearer ${adminToken}`
            },
            body: newPatient
        }).then((response) => {
            expect(response.status).to.eq(201);
            expect(response.body).to.have.property('id');
            const newPatientId = response.body.id;
            cy.log(`New patient ID: ${newPatientId}`);

            cy.task('setPatientData', { ...newPatient, id: newPatientId });
        });
    });
});
