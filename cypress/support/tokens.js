let tokens = {};

const setToken = (role, token) => {
    tokens[role] = token;
};

const getToken = (role) => {
    return tokens[role];
};

module.exports = { setToken, getToken };
