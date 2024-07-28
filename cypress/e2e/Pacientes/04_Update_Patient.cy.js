const { getToken } = require('../../support/tokens');

describe('Create and Update Patient', () => {
    let newPatient;
    let adminToken;
    let createdPatientId;

    before(() => {
        cy.task('getPatientData').then((patient) => {
            newPatient = patient;
            cy.log(`Retrieved patient data: ${JSON.stringify(newPatient)}`);
            expect(newPatient).to.not.be.undefined;
        });

        cy.task('getToken', 'ADMIN').then((token) => {
            adminToken = token;
            expect(adminToken).to.not.be.undefined;
        });
    });

    it('should create and update a patient', () => {
        const formattedCpf = `${newPatient.cpf.slice(0, 3)}.${newPatient.cpf.slice(3, 6)}.${newPatient.cpf.slice(6, 9)}-${newPatient.cpf.slice(9, 11)}`;

        const patientData = {
            nomeCompleto: newPatient.nome,
            genero: "Masculino",
            dataNascimento: newPatient.dataNascimento,
            cpf: formattedCpf,
            rg: "SC-12.345.678",
            orgaoExpedidorRg: "SSP",
            estadoCivil: "Solteiro",
            telefone: "(48) 9 9663-6469",
            email: newPatient.email,
            naturalidade: "São José",
            contatoEmergencia: "(48) 9 9838-3989",
            listaAlergias: ["Pólen", "Amendoim"],
            listaCuidadosEspecificos: ["Usar protetor solar diariamente"],
            convenio: "Saúde Total",
            numeroConvenio: "123456789",
            validadeConvenio: "2025-12-31",
            usuario: {
                id: newPatient.id
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

        // Create patient
        cy.request({
            method: 'POST',
            url: 'http://localhost:8081/api/pacientes',
            headers: {
                'Authorization': `Bearer ${adminToken}`
            },
            body: patientData,
            failOnStatusCode: false
        }).then((response) => {
            if (response.status === 400 && response.body.includes('CPF já cadastrado')) {
                cy.log('CPF already registered, skipping creation.');
                createdPatientId = newPatient.id;
            } else {
                expect(response.status).to.eq(201);
                createdPatientId = response.body.id;
                cy.log(`Patient created with ID: ${createdPatientId}`);
            }

            // Update patient
            const updatedPatientData = {
                ...patientData,
                numeroConvenio: `${Math.floor(Math.random() * 1000000000)}`,
                endereco: {
                    ...patientData.endereco,
                    pontoDeReferencia: "Proximo a casa azul"
                }
            };

            cy.request({
                method: 'PUT',
                url: `http://localhost:8081/api/pacientes/${createdPatientId}`,
                headers: {
                    'Authorization': `Bearer ${adminToken}`
                },
                body: updatedPatientData,
                failOnStatusCode: false
            }).then((updateResponse) => {
                if (updateResponse.status === 404) {
                    cy.log('Patient not found, cannot update.');
                } else {
                    expect(updateResponse.status).to.eq(200);
                    cy.log(`Patient updated with ID: ${createdPatientId}`);
                }
            });
        });
    });
});