import { useEffect, useState } from 'react'

import styles from '../../styles/freshAdded.module.scss'
import Card from './card.js'

const FreshAdded = (props) => {

    const [list, setList] = useState([])


    useEffect(() => {
        fetch(process.env.SERVER_API_URL + '/announcement/0/10')
            .then(response => {
                console.log(response)
                response.json().then(
                    res => {
                        setList(res)
                        console.log(res)
                    }
                )
            })
            .catch(error => console.log(error))
    }, [])


    return (
        <div className={styles.freshAdded}>
            <div className={styles.header}>
                <h1>
                    Fresh added
                </h1>
                <p>
                    Added in the last 24 hours.
                </p>
            </div>

            <div className={styles.carousel}>
                {list.length===0 && <img src="loader.gif"/>}
                {list.map((item, key) => {
                    return (
                        <Card
                            key={key}
                            img={(process.env.SERVER_API_URL+'/announcement/thumbnail/'+item.id)}//https:// via.placeholder.com/217x305
                            name={item.name}
                            category={item.category}
                            price={item.pricePerDay}
                        />
                    )
                }
                )
                }
            </div>

            <div className={styles.button}>
                <button>See inventory</button>
            </div>

        </div>

    )
}

export default FreshAdded