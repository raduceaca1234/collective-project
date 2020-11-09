import '../styles/global.scss'

import Navbar from '../components/navbar'
import Header from '../components/header'

function MyApp({ Component, pageProps }) {
  return (
    <div>
      <Navbar />
      <Component {...pageProps} />
    </div>
  )

}

export default MyApp
