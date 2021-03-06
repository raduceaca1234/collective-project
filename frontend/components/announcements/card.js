import styles from '../../styles/card.module.scss'
import { useRouter } from 'next/router'

const PLACEHOLDER = 'https://via.placeholder.com/217x305'

const Card = (props) => {
    const router = useRouter()

    return (
        <div
            className = {styles.card}
            onClick={() => router.push(`/p/${props.id}`)}    
        >
            <div className={styles.image}>
                <img src={props.img} onError={(e)=>{e.target.onerror=null; e.target.src=PLACEHOLDER;}}/>
            </div>

            <div className = {styles.details}>
                <h2>{props.name}</h2>
                <p>in {props.category}</p>
                <h3>$ {props.price}/h</h3>
            </div>

        </div>
    )
}

export default Card