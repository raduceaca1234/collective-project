import Head from 'next/head'
import styles from '../styles/Home.module.scss'

const Index = () => {
  return (
    <div className={"grid pagesContainer " + styles.container}>
      <div className={"col-6" + ' ' + styles.rediv}>
        col6
      </div>

      <div className={"col-6" + ' ' + styles.bluediv}>
        col6  
      </div>
    </div>
  )
}

export default Index
