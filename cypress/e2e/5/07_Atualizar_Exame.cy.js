const { faker } = require('@faker-js/faker');
const { setToken, getToken } = require('../../support/tokens');

describe('Atualizar exame com perfil de admin', () => {
    const loginUrl = 'http://localhost:8081/api/usuarios/login';
    const examesUrl = 'http://localhost:8081/api/exames';
    const novoNomeExame = faker.lorem.words(3);
    const novaDataExame = '2023-12-02';
    const novoHorarioExame = '14:30:00';
    const novoTipoExame = faker.lorem.words(3);
    const novoLaboratorio = 'Laboratório XYZ';
    const novaUrlDocumento = 'http://example.com/documento.pdf';
    const novosResultados = 'Resultados do exame...';
    let adminToken;
    let createdExameId;

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
            cy.task('setToken', { role: 'ADMIN', token: adminToken });
        });

        cy.task('queryDatabase', `SELECT id FROM exames LIMIT 1`).then((result) => {
            expect(result).to.have.length(1);
            createdExameId = result[0].id;
            cy.log(`Exame ID selecionado: ${createdExameId}`);
        });
    });

    it('deve retornar 200 ao atualizar um exame', () => {
        cy.request({
            method: 'PUT',
            url: `${examesUrl}/${createdExameId}`,
            headers: {
                'Authorization': `Bearer ${adminToken}`
            },
            body: {
                nomeExame: novoNomeExame,
                dataExame: novaDataExame,
                horarioExame: novoHorarioExame,
                tipoExame: novoTipoExame,
                laboratorio: novoLaboratorio,
                urlDocumento: novaUrlDocumento,
                resultados: novosResultados,
                pacienteId: 2
            }
        }).then((response) => {
            expect(response.status).to.eq(200);

            expect(response.body).to.have.property('dados');
            expect(response.body.dados).to.have.property('nomeExame', novoNomeExame);
            expect(response.body.dados).to.have.property('dataExame', novaDataExame);
            expect(response.body.dados).to.have.property('resultados', novosResultados);
            cy.log('Exame atualizado com sucesso!');

            cy.task('queryDatabase', `SELECT nome_exame, data_exame, resultados 
                                      FROM exames 
                                      WHERE id = ${createdExameId}`).then((result) => {
                expect(result).to.have.length(1);
                expect(result[0].nome_exame).to.eq(novoNomeExame);
                expect(result[0].data_exame.split('T')[0]).to.eq(novaDataExame);
                expect(result[0].resultados).to.eq(novosResultados);
                cy.log(`Exame atualizado verificado: ${novoNomeExame}, ${novaDataExame}, ${novosResultados}`);
            });
        });
    });
});
