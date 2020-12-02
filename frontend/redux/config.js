import { combineReducers, compose, applyMiddleware } from 'redux'
import { createStore } from 'redux'
import { persistStore, persistReducer } from 'redux-persist'
import storage from 'redux-persist/lib/storage'

import userReducer from './user/actions'

const reducers = combineReducers({
    user: userReducer,
})

const persistConfig = {
    key: 'root',
    storage,          
}
   
const persistedReducer = persistReducer(persistConfig, reducers)

const store = createStore(persistedReducer)

const persistor = persistStore(store)

export default { store, persistor }