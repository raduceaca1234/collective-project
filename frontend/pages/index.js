import Head from 'next/head'
import styles from '../styles/Home.module.scss'

import Header from '../components/header'
import Footer from '../components/footer'
import CTA from '../components/announcements/cta'
import Fresh from '../components/announcements/freshAdded'
import Login from '../components/register/login'
import SignUp from '../components/register/signup'


const Index = () => {
  return (
    <div>
      <Login/>
      <SignUp/>
      {/* <Header />
      <Fresh />
      <CTA />
      <Footer /> */}

    </div>
  )
}

export default Index
