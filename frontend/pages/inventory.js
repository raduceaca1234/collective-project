import { useState, useEffect } from 'react'

import styles from '../styles/inventory.module.scss'

import InvAPI from '../api/inventory'

import Card from '../components/announcements/card'

const Inventory = () => {
    const [page, setPage] = useState(0)
    const [content, setContent] = useState([])
    const [showBtn, setShowBtn] = useState(true)
    const [filterList, setFilter] = useState()
    const [priceRange, setPriceRange] = useState({ minLimit: null, maxLimit: null })
    const [minDays, setMinDays] = useState()
    const [order, setOrder] = useState(null)
    
    const toggleFilter = e => {
        console.log(e.target.value)//, e.target.checked)

        if(e.target.checked === false) {
            setFilter(filterList.filter(each => each !== e.target.value))
        } else {
            setFilter(filterList ? [...filterList, e.target.value] : [e.target.value])
        }
    }

    const changePriceRange = e => {
        setPriceRange({
            ...priceRange,
            [e.target.name]: (e.target.value !== ""? parseInt(e.target.value): null),
        })
        // console.log(priceRange, e.target.name, e.target.value)
    }

    const changeOrderBy = e => {
        const value = e.target.value.split(',')

        setOrder({
            field: value[0],
            order: value[1],
        })
    }

    useEffect(() => {
        console.log("mount")
        loadMore()
    }, [])

    useEffect(() => {
        setPage(0)
        setContent([])
        loadMore(0)
    }, [filterList, priceRange, minDays, order])

    const loadMore = (forcePage = null) => {
        console.log("forcepage", forcePage)
        let options = {
            pageSize: 10,
            pageNumber: forcePage !== null ? forcePage: page,
            ordering: order,
            priceFiltering: priceRange,
            categoryFiltering: {
                categories: filterList,
                // AGRICULTURE, AUTO_MOTO, TECH, CULTURE, SPORT
            },
            durationFiltering: {
                minDays: (minDays > 0? minDays: null),
            },
        }

        console.log(options)

        InvAPI.getPage(options)
        .then(res => {
            if(res?.length > 0) {
                forcePage === 0 ? setContent([...res]) : setContent([...content, ...res])
            
                if(res.length < 10) {
                    setShowBtn(false)
                }
            }
        })
        .catch(err => {
            console.log(err)
        })
    }

    return (
        <div className={styles.content}>
            <h1>Search items</h1>

            <div className={styles.grid}>
                <div className={styles.leftPane}>
                    <h3>Filter &amp; Order</h3>
                    <div className={styles.group}>
                        <label>Order results by</label>
                        <select
                            onChange={changeOrderBy}
                        >
                            <option value="pricePerDay,ASC">Price Ascending</option>
                            <option value="pricePerDay,DESC">Price Descending</option>
                        </select>
                    </div>

                    <div className={styles.group}>
                        <label>Price range</label>
                        <div className={styles.inline}>
                            <input type="text" onChange={changePriceRange} name="minLimit" placeholder="min"/>
                            <input type="text" onChange={changePriceRange} name="maxLimit" placeholder="max"/>
                        </div>
                    </div>

                    <div className={styles.group}>
                        <label>Categories</label>
                        <div className={styles.inline}>
                            <input type="checkbox" onChange={toggleFilter} id="AGRICULTURE" value="AGRICULTURE"/>
                            <label htmlFor="AGRICULTURE">Agriculture</label>
                        </div>

                        <div className={styles.inline}>
                            <input type="checkbox" onChange={toggleFilter} id="AUTO_MOTO" value="AUTO_MOTO"/>
                            <label htmlFor="AUTO_MOTO">Auto-mott</label>
                        </div>

                        <div className={styles.inline}>
                            <input type="checkbox" onChange={toggleFilter} id="TECH" value="TECH"/>
                            <label htmlFor="TECH">Tech</label>
                        </div>

                        <div className={styles.inline}>
                            <input type="checkbox" onChange={toggleFilter} id="CULTURE" value="CULTURE"/>
                            <label htmlFor="CULTURE">Culture</label>
                        </div>

                        <div className={styles.inline}>
                            <input type="checkbox" onChange={toggleFilter} id="SPORT" value="Sport"/>
                            <label htmlFor="SPORT">Sport</label>
                        </div>
                    </div>

                    <div className={styles.group}>
                        <label>Availability(days)</label>
                        <input type="number" onChange={e => setMinDays(e.target.value)} name="duration" placeholder="0"/>
                    </div>
                </div>

                <div className={styles.products}>
                    <div className={styles.list}>
                        {
                            content.map(e => (
                                <Card
                                    key={e.id}
                                    img={(process.env.SERVER_API_URL+'/announcement/thumbnail/'+e.id)}//https:// via.placeholder.com/217x305
                                    name={e.name}
                                    category={e.category}
                                    price={e.pricePerDay}
                                />
                                
                            ))
                        }
                    </div>

                    { showBtn ? <button onClick={() => {setPage(page + 1); loadMore()}}>Load more</button> : <p>You have reached the ocean bottom.</p>}
                </div>
            </div>
        </div>
    )
}

export default Inventory