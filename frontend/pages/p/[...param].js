import React, { useState, useRef } from 'react'
import styles from '../../styles/product_page.module.scss'
import { useRouter } from 'next/router'

import Gallery from '../../components/productView/gallery'
import Contact from '../../components/productView/contact'
import Details from '../../components/productView/details'
import Demands from '../../components/productView/demands'

import Footer from '../../components/footer'

import Alert from '../../components/productView/alert'

const Product = () => {
    const router = useRouter()
    
    const contact = useRef()
    const [alert, setAlert] = useState()

    const clearAlert = () => {
        setAlert()
    }

    const { prod } = router.query

    console.log(router.query)

    const onClickBorrow = () => {
        setAlert({
            title: '🤳🏼 Hello..?',
            message: 'A call was made on this product. You will be contacted by the owner as soon as he sees your request.',
        })
    }

    return (
        <>
            <div className={styles.container}>
                <div className="grid">
                    <div className={"col-12 " + styles.prod_page_controlls}>
                        <div className={styles.prod_page_controlls_lhs}>
                            <h1>Product Name</h1>
                            <p>Added on 28 Oct. at 12:03 PM</p>
                        </div>

                        <div className={styles.prod_page_controlls_rhs}>
                            {
                                true &&
                                <>
                                    <button onClick={() => contact.current?.scrollIntoView()}>Contact</button>
                                    <button onClick={() => onClickBorrow()}>Borrow</button>
                                </>
                            }
                            {
                                true &&
                                <>
                                    <button><i>+9</i>Demands</button>
                                    <button>Manage</button>
                                </>
                            }
                        </div>
                    </div>
                </div>

                <div className={"grid " + styles.prod_content}>
                    <div className="col-8">
                        {
                            router.query.param?.length > 1 && router.query.param[1] === 'demand' ?
                            <>
                                <Demands />
                            </>
                            :
                            <>
                                <Gallery />
                                <Contact headref={contact}/>
                            </>
                        }
                    </div>

                    <div className={"col-4 " + styles.details}>
                        <Details />
                    </div>
                </div>
            </div>
            <Footer />
            { alert && <Alert data={alert} onClose={clearAlert} /> }
        </>
    )
}

export default Product
