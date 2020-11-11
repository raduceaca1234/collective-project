import '../styles/global.scss'

import Navbar from '../components/navbar'
import Header from '../components/header'
import Footer from '../components/footer'
import Cta from '../components/announcements/cta'

function MyApp({ Component, pageProps }) {
  return (
    <div>
      <Navbar />
      <Component {...pageProps} />
      <Header></Header>
      <Cta></Cta>
      <Footer></Footer>
      
    </div>
  )

}

export default MyApp
