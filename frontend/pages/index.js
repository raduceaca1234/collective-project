import Head from 'next/head'
import styles from '../styles/Home.module.scss'

import Header from '../components/header'
import Footer from '../components/footer'
import CTA from '../components/announcements/cta'
import Fresh from '../components/announcements/freshAdded'


const Index = () => {
  return (
    <div>
      <Header />
      <Fresh />
      <CTA />
      <Footer />
    </div>
  )
}

export default Index
