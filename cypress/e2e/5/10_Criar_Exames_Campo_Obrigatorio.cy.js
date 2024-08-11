const { faker } = require('@faker-js/faker');
const { setToken, getToken } = require('../../support/tokens');

describe('Criar exame com perfil de admin - erro 400', () => {
    const loginUrl = 'http://localhost:8081/api/usuarios/login';
    const examesUrl = 'http://localhost:8081/api/exames';
    let adminToken;

    before(() => {
        cy.request({
            method: 'POST',
            url: loginUrl,
            body: {
                email: 'admin@example.com',
                password: 'admin'
            }
        }).then((response) => {
            expect(response.status).to.eq(200);
            expect(response.body).to.have.property('token');
            adminToken = response.body.token;
            cy.log(`Token para ADMIN: ${adminToken}`);
            cy.task('setToken', { role: 'ADMIN', token: adminToken });
        });
    });

    it('deve retornar 404 ao criar um exame com nomeExame vazio', () => {
        cy.task('getToken', 'ADMIN').then((token) => {
            const nomeExame = "";
            const dataExame = faker.date.future().toISOString().split('T')[0];
            const horarioExame = faker.date.recent().toISOString().split('T')[1].split('.')[0];
            const tipoExame = "Sangue";
            const laboratorio = "Laboratório XYZ";
            const urlDocumento = "http://example.com/documento.pdf";
            const resultados = faker.lorem.sentence();

            cy.request({
                method: 'POST',
                url: examesUrl,
                headers: {
                    'Authorization': `Bearer ${token}`
                },
                body: {
                    nomeExame,
                    dataExame,
                    horarioExame,
                    tipoExame,
                    laboratorio,
                    urlDocumento,
                    resultados,
                    pacienteId: 2
                },
                failOnStatusCode: false
            }).then((response) => {
                expect(response.status).to.eq(404);
                cy.log('Erro 404 retornado como esperado: Nome do exame é obrigatório');
            });
        });
    });
});
