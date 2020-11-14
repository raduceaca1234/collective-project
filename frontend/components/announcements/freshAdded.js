import styles from '../../styles/freshAdded.module.scss'
import Card from './card.js'

const FreshAdded = (props) => {

    const cards = () => {
        const element = []
        for (let index = 0; index < 10; index++) {
            element.push(
                <Card
                    img = {"data:image/jpeg;base64,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"}
                    name = {"Chitara"}
                    category = {"Instrumente"}
                    price = {22}
            />
            )
        }
        return element
    }

    return(
        <div className = {styles.freshAdded}>
            <div className = {styles.header}>
                <h1>
                    Fresh added
                </h1>
                <p>
                    Added in the last 24 hours.
                </p>
            </div>

            <div className = {styles.carousel}>
                {cards()}
            </div>

            <div className = {styles.button}>
                <button>See inventory</button>
            </div>

        </div>

    )
}

export default FreshAdded