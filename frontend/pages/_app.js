import '../styles/global.scss'

import { Provider } from 'react-redux'
import { PersistGate } from 'redux-persist/integration/react'
import reduxConfig from '../redux/config'

import Navbar from '../components/navbar'

function MyApp({ Component, pageProps }) {

  return (
    <Provider store={reduxConfig.store}>
      <PersistGate
        loading={(<div>loading</div>)}
        persistor={reduxConfig.persistor}
      >
        <Navbar />
        <Component {...pageProps} />  
      </PersistGate>
    </Provider>
  )

}

export default MyApp
