

const signIn = (data) => {
    return fetch(process.env.SERVER_API_URL+'/user/login',
    {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(data),
    })
    .then(res => res.json())
}

const signUp = (data) => {
    return fetch(process.env.SERVER_API_URL+'/user/register',
    {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(data),
    })
}

export default {
    signIn,
    signUp,
}