const { setToken, getToken } = require('../../support/tokens');

// Teste para autenticar usuários e listar pacientes
describe('Listar Pacientes', () => {
    const baseUrl = 'http://localhost:8081/api/usuarios/login';
    let adminToken;

    // Antes de todos os testes, logar e pegar o token do administrador
    before(() => {
        cy.request({
            method: 'POST',
            url: baseUrl,
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

    it('deve retornar 200 ao acessar a lista de pacientes /api/pacientes e validar a paginação', () => {
        cy.task('getToken', 'ADMIN').then((token) => {
            cy.request({
                method: 'GET',
                url: 'http://localhost:8081/api/pacientes',
                headers: {
                    'Authorization': `Bearer ${token}`
                },
            }).then((response) => {
                expect(response.status).to.eq(200);
                expect(response.body).to.have.property('mensagem', 'Lista de pacientes recuperada com sucesso');
                expect(response.body).to.have.property('dados');
                expect(response.body.dados).to.have.property('content').and.to.be.an('array');
                expect(response.body.dados).to.have.property('pageable');
                expect(response.body.dados).to.have.property('totalElements').and.to.be.a('number');
                expect(response.body.dados).to.have.property('totalPages').and.to.be.a('number');
                expect(response.body.dados).to.have.property('size').and.to.be.a('number');
                expect(response.body.dados).to.have.property('number').and.to.be.a('number');
                expect(response.body.dados).to.have.property('sort');
                expect(response.body.dados).to.have.property('first').and.to.be.a('boolean');
                expect(response.body.dados).to.have.property('last').and.to.be.a('boolean');
                expect(response.body.dados).to.have.property('numberOfElements').and.to.be.a('number');
                expect(response.body.dados).to.have.property('empty').and.to.be.a('boolean');
            });
        });
    });
});