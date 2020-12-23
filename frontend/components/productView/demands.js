import React, { useEffect, useState } from 'react'
import PropTypes from 'prop-types'

import styles from '../../styles/demands.module.scss'

const Demands = props => {
    const [discussions, setDiscussions] = useState([])
    const [loan, setLoan] = useState()

    const fetchData = () => {
        setLoan(null)
        fetch(process.env.SERVER_API_URL+`/announcement/interestedIn/${props.anId}`,
        {
            method: 'GET',
        })
        .then(res => res.json())
        .then(res => {
            loan?
            setDiscussions(
                discussions.map(e => {
                    if(e.discussionId === loan.discussionId) return {
                        ...e,
                        loan: true,
                    }
                    return e;
                })
            )
            :
            setDiscussions(res)
        })
        .catch(err => {
            console.log(err)
        })
    }

    useEffect(() => {
        fetchData()
    }, [])

    useEffect(() => {
        if(discussions.length > 0 && !loan) {
            fetch(process.env.SERVER_API_URL+`/announcement/loans/${props.anId}`,
            {
                method: 'GET',
            })
            .then(res => res.json())
            .then(res => {
                setLoan(res)
                console.log(res)
                res && setDiscussions(
                    discussions.map(e => {
                        if(e.discussionId === res.discussionId) return {
                            ...e,
                            loan: true,
                        }
                        return e;
                    })
                )
            })
            .catch(err => {
                console.log(err)
            })
        }
    }, [discussions])

    const onClickAccept = (token, id) => {
        let data = {
            interestedTokenUser: token, 
            announcementId: id,
        }

        fetch(process.env.SERVER_API_URL+`/announcement/loan`,
        {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(data),
        })
        .then(res => {
            alert("Your object was given to an user. Now, it's availability will be set off.")
            fetchData()
        })
        .catch(err => {
            alert("Something went wrong...")
            fetchData()
        })
    }

    const onClickCancel = (token, id) => {
        let data = {
            interestedTokenUser: token, 
            announcementId: id,
        }

        fetch(process.env.SERVER_API_URL+`/announcement/closed-loan`,
        {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(data),
        })
        .then(res => {
            alert("Loan was canceled. Your object is now available.")
            fetchData()
        })
        .catch(err => {
            alert("Something went wrong...")
            fetchData()
        })
    }

    return (
        <div className={styles.container}>
            <h3>Demands</h3>

            <ul className={styles.demands_list}>
                {
                    console.log(discussions)
                }
                {
                    discussions.map?.((e, i) => (
                        <li key={i} className={e.loan?styles.loan:''}>
                            <h3>{e.email}</h3>

                            <i>For 20 Oct 2020</i>

                            <button onClick={() => e.loan?onClickCancel(e.token, props.anId):onClickAccept(e.token, props.anId)}>{e.loan?'Cancel':'Accept'}</button>
                        </li>
                    ))
                }
            </ul>
        </div>
    )
}

Demands.propTypes = {

}

export default Demands
