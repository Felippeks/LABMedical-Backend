const { setToken, getToken } = require('../../support/tokens');

// Teste para autenticar usuários e gerenciar pacientes
describe('Autenticação de Usuário e Gerenciamento de Pacientes', () => {
    const baseUrl = 'http://localhost:8081/api/usuarios/login';
    let patientToken;
    let adminToken;
    let createdPatientId;

    // Antes de todos os testes, logar e pegar os tokens do paciente e do administrador
    before(() => {
        // Logar e pegar o token do paciente
        cy.request({
            method: 'POST',
            url: baseUrl,
            body: {
                email: 'paciente@example.com',
                password: 'paciente'
            }
        }).then((response) => {
            expect(response.status).to.eq(200);
            expect(response.body).to.have.property('token');
            patientToken = response.body.token;
            cy.log(`Token para PACIENTE: ${patientToken}`);
            cy.task('setToken', { role: 'PACIENTE', token: patientToken });
        });

        // Logar e pegar o token do administrador
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

    // Antes de todos os testes, criar um novo paciente
    it('deve criar um novo paciente usando os dados armazenados e salvar o ID do paciente', () => {
        cy.task('getPatientData').then((newPatient) => {
            const patientData = {
                nomeCompleto: "Paciente01",
                genero: "Masculino",
                dataNascimento: "1970-01-01",
                cpf: "000.000.000-01",
                rg: "SC-12.345.678",
                orgaoExpedidorRg: "SSP",
                estadoCivil: "Solteiro",
                telefone: "(48) 9 9663-6469",
                email: "paciente@example.com",
                naturalidade: "São José",
                contatoEmergencia: "(48) 9 9838-3989",
                listaAlergias: ["Pólen", "Amendoim"],
                listaCuidadosEspecificos: ["Usar protetor solar diariamente"],
                convenio: "Saúde Total",
                numeroConvenio: "123456789",
                validadeConvenio: "2025-12-31",
                usuario: {
                    id: "2"
                },
                endereco: {
                    logradouro: "Rua das Flores",
                    numero: "123",
                    complemento: "Apto 101",
                    bairro: "Jardim das Árvores",
                    cidade: "Belo Horizonte",
                    estado: "MG",
                    cep: "31270-010"
                }
            };

            // Verificar se o paciente já existe
            cy.task('queryDatabase', `SELECT id FROM pacientes WHERE cpf = '${patientData.cpf}'`).then((result) => {
                if (result.length > 0) {
                    createdPatientId = result[0].id;
                    cy.log(`CPF já cadastrado, usando ID de paciente existente: ${createdPatientId}`);
                    cy.task('savePatientId', createdPatientId);
                } else {
                    cy.task('getToken', 'ADMIN').then((adminToken) => {
                        cy.request({
                            method: 'POST',
                            url: 'http://localhost:8081/api/pacientes',
                            headers: {
                                'Authorization': `Bearer ${adminToken}`
                            },
                            body: patientData
                        }).then((response) => {
                            expect(response.status).to.eq(201);
                            createdPatientId = response.body.id;
                            cy.log(`Paciente criado com ID: ${createdPatientId}`);
                            cy.task('savePatientId', createdPatientId);
                        });
                    });
                }
            });
        });
    });

    // Teste de acesso não autorizado
    it('deve retornar 403 ao acessar /api/pacientes sem autorização', () => {
        cy.task('getToken', 'PACIENTE').then((token) => {
            cy.request({
                method: 'GET',
                url: 'http://localhost:8081/api/pacientes',
                headers: {
                    'Authorization': `Bearer ${token}`
                },
                failOnStatusCode: false
            }).then((response) => {
                expect(response.status).to.eq(403);
            });
        });
    });

    // Teste de acesso autorizado
    it('deve retornar os dados do paciente ao acessar /api/paciente/id com o token do paciente', () => {
        cy.task('queryDatabase', `SELECT id FROM pacientes WHERE cpf = '000.000.000-01'`).then((result) => {
            const createdPatientId = result[0].id;
            cy.request({
                method: 'GET',
                url: `http://localhost:8081/api/pacientes/${createdPatientId}`,
                headers: {
                    'Authorization': `Bearer ${patientToken}`
                }
            }).then((response) => {
                expect(response.status).to.eq(200);
            });
        });
    });

    // Teste de acesso não autorizado
    it('deve retornar 403 ao acessar /api/paciente/id diferente com o token do paciente', () => {
        cy.request({
            method: 'GET',
            url: `http://localhost:8081/api/pacientes/234`,
            headers: {
                'Authorization': `Bearer ${patientToken}`
            },
            failOnStatusCode: false
        }).then((response) => {
            expect(response.status).to.eq(403);
        });
    });
});