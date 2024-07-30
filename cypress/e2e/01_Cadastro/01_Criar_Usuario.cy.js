const { getToken } = require('../../support/tokens');

describe('Registro de Usuário Paciente', () => {
    let adminToken;

    // Antes de todos os testes, obtenha o token de administrador
    before(() => {
        cy.task('getToken', 'ADMIN').then((token) => {
            adminToken = token;
            expect(adminToken).to.not.be.undefined;
        });
    });

    // Teste para registrar um novo paciente
    it('deve registrar um novo paciente usando o token de administrador', () => {
        const randomSuffix = Math.floor(Math.random() * 10000).toString().padStart(4, '0');
        const newPatient = {
            nome: `jose silva ${randomSuffix}`,
            email: `jose${randomSuffix}@gmail.com`,
            dataNascimento: '1991-04-01',
            cpf: `0666914${randomSuffix}`,
            password: '1q2w3e@!',
            perfil: 'PACIENTE'
        };

        // Envia uma requisição POST para registrar um novo paciente
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
            cy.log(`Novo ID de paciente: ${newPatientId}`);

            // Salva os dados do novo paciente
            cy.task('setPatientData', { ...newPatient, id: newPatientId });
        });
    });
});