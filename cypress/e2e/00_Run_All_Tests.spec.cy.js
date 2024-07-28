describe('Run All Tests', () => {
    it('should run all test files sequentially', () => {
        const testFiles = [
            'cypress/e2e/Pacientes/01_User_Authentication.cy.js',
            'cypress/e2e/Pacientes/02_CreateUser_paciente.cy.js',
            'cypress/e2e/Pacientes/03_Create_Paciente_Register.cy.js',
            'cypress/e2e/Pacientes/04_Update_Patient.cy.js',
            'cypress/e2e/Pacientes/05_Delete_Patient.cy.js'
        ];

        testFiles.reduce((promise, file) => {
            return promise.then(() => {
                return cy.exec(`npx cypress run --spec ${file}`, { failOnNonZeroExit: false }).then((result) => {
                    if (result.code !== 0) {
                        const log = `Test file ${file} failed with code ${result.code}\nStdout: ${result.stdout}\nStderr: ${result.stderr}\n`;
                        cy.log(log);
                        cy.writeFile(`cypress/logs/${file.replace(/\//g, '_')}.log`, log);
                    }
                    expect(result.code).to.eq(0);
                });
            });
        }, Cypress.Promise.resolve());
    });
});
