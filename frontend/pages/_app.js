import '../styles/global.scss'

import Navbar from '../components/navbar'
import Header from '../components/header'
import Footer from '../components/footer'
import Cta from '../components/announcements/cta'
import Contact from '../components/productView/contact'

function MyApp({ Component, pageProps }) {
  return (
    <div>
      <Navbar />
      <Component {...pageProps} />
      
    </div>
  )

}

export default MyApp
