import styles from '../../styles/card.module.scss'

const Card = (props) => {
    
    return (
        <div className = {styles.card}>
            <div className = {styles.image}>
                <img  src = {props.img}/>
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