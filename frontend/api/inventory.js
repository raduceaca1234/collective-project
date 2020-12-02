


const getPage = (data) => {
    return fetch(process.env.SERVER_API_URL+'/announcement/custom',
    {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(data),
    })
    .then(res => res.json())
}

export default {
    getPage,

}