export const actions = {
    USER_SET_AUTH: 'USER_SET_AUTH',
    USER_SET_DATA: 'USER_SET_DATA',
    USER_SET_TOKEN: 'USER_SET_TOKEN',
    USER_SET_EXPIRE: 'USER_SET_EXPIRE',
    USER_LOGOUT: 'USER_LOGOUT',
}

const DEFAULT_STATE = {
    data: null,
    token: null,
    logged_in: false,
    expiry: null,
}

const userActions = (state = DEFAULT_STATE, action) => {
    switch (action.type) {
        case actions.USER_SET_AUTH:
            return {
                ...state,
                logged_in: action.payload,
            }
        case actions.USER_SET_DATA:
            return {
                ...state,
                data: action.payload,
            }
        case actions.USER_SET_TOKEN:
            return {
                ...state,
                token: action.payload,
            }
        case actions.USER_SET_EXPIRE:
            return {
                ...state,
                expiry: action.payload,
            }
        case actions.USER_LOGOUT:
            return DEFAULT_STATE
        default:
            return state;
    }
}

export default userActions