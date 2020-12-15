import React from 'react'
import PropTypes from 'prop-types'

import styles from '../../styles/demands.module.scss'

const Demands = props => {
    return (
        <div className={styles.container}>
            <h3>Demands</h3>

            <ul className={styles.demands_list}>
                <li>
                    <h3>Full Name</h3>

                    <i>For 20 Oct 2020</i>

                    <button>Accept</button>
                </li>

                <li>
                    <h3>Full Name</h3>

                    <i>For 20 Oct 2020</i>

                    <button>Accept</button>
                </li>
            </ul>
        </div>
    )
}

Demands.propTypes = {

}

export default Demands
