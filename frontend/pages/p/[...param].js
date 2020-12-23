import React, { useState, useEffect, useRef } from 'react'
import styles from '../../styles/product_page.module.scss'
import { useRouter } from 'next/router'
import { useDispatch, useSelector } from 'react-redux'

import Gallery from '../../components/productView/gallery'
import Contact from '../../components/productView/contact'
import Details from '../../components/productView/details'
import Demands from '../../components/productView/demands'

import Footer from '../../components/footer'

import Alert from '../../components/productView/alert'

const Product = () => {
    const router = useRouter()
    const user = useSelector(state => (state.user))
    
    const contact = useRef()
    const [alert, setAlert] = useState()
    const [announcement, setAnnouncement] = useState()

    const clearAlert = () => {
        setAlert()
    }

    const { param } = router.query;

    const onClickBorrow = () => {
        let data = {
            interestedTokenUser: user.token,
            announcementId: router.query.param[0],
        }

        fetch(process.env.SERVER_API_URL+'/announcement/discussion',
        {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(data),
        })
        .then(res => {
            setAlert({
                title: '🤳🏼 Hello..?',
                message: 'A call was made on this product. You will be contacted by the owner as soon as he sees your request.',
            })
        })
        .catch(err => {
            console.log(err)
        })

    }

    useEffect(() => {
        if(param) {
            fetch(process.env.SERVER_API_URL+`/announcement/${router.query.param[0]}`,
            {
                method: 'GET',
                headers: {
                    token: user.token,
                }
            })
            .then(res => res.json())
            .then(res => {
                console.log(res)
                setAnnouncement(res)
            })
            .catch(err => {
                console.log(err)
            })
        }
        
    }, [param])

    const isOwner = () => {
        return announcement?.ownerId === user.token
    }

    return (
        <>
            <div className={styles.container}>
                <div className="grid">
                    <div className={"col-12 " + styles.prod_page_controlls}>
                        <div className={styles.prod_page_controlls_lhs}>
                            <h1>{announcement?.name}</h1>
                            {/* <p>Added on 28 Oct. at 12:03 PM</p> */}
                        </div>

                        <div className={styles.prod_page_controlls_rhs}>
                            {
                                !isOwner() &&
                                <>
                                    <button onClick={() => contact.current?.scrollIntoView()}>Contact</button>
                                    <button onClick={() => onClickBorrow()}>Borrow</button>
                                </>
                            }
                            {
                                isOwner() &&
                                <>
                                    <button onClick={() => router.push(`/p/${param[0]}/demands`)}><i>+9</i>Demands</button>
                                    <button>Manage</button>
                                </>
                            }
                        </div>
                    </div>
                </div>

                <div className={"grid " + styles.prod_content}>
                    <div className="col-8">
                        {
                            param?.[1] === 'demands' ?
                            <>
                                <Demands data={announcement} anId={router.query.param[0]}/>
                            </>
                            :
                            <>
                                <Gallery />
                                <Contact headref={contact}/>
                            </>
                        }
                    </div>

                    <div className={"col-4 " + styles.details}>
                        <Details data={announcement}/>
                    </div>
                </div>
            </div>
            <Footer />
            { alert && <Alert data={alert} onClose={clearAlert} /> }
        </>
    )
}

export default Product
