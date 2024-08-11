const { setToken, getToken } = require('../../support/tokens');

describe('Dashboard Estatísticas', () => {
    const loginUrl = 'http://localhost:8081/api/usuarios/login';
    const dashboardUrl = 'http://localhost:8081/api/dashboard';
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
            cy.task('setToken', {role: 'ADMIN', token: adminToken});
        });
    });

    it('deve retornar 200 ao acessar o dashboard', () => {
        cy.task('getToken', 'ADMIN').then((token) => {
            cy.request({
                method: 'GET',
                url: dashboardUrl,
                headers: {
                    'Authorization': `Bearer ${token}`
                }
            }).then((response) => {
                expect(response.status).to.eq(200);
                expect(response.body).to.have.property('mensagem', 'Dashboard estatiticas');
                expect(response.body).to.have.property('dados');
                expect(response.body.dados).to.have.property('totalPacientes').that.is.a('number');
                expect(response.body.dados).to.have.property('totalConsultas').that.is.a('number');
                expect(response.body.dados).to.have.property('totalExames').that.is.a('number');
                cy.log(response.body);
            });
        });
    });
});
