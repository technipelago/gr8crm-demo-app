order = 100000

config = {

    defaults {
        eternal false
        overflowToDisk false
        maxElementsInMemory 1000
        maxElementsOnDisk 0
        timeToIdleSeconds 600
        timeToLiveSeconds 600
    }

    diskStore {
        path "${System.getProperty('java.io.tmpdir')}gr8crm"
    }

    cache {
        name 'org.hibernate.cache.StandardQueryCache'
        eternal true
        overflowToDisk true
        maxElementsInMemory 5000
        maxElementsOnDisk 10000
        timeToIdleSeconds 0
        timeToLiveSeconds 0
    }

    cache {
        name 'org.hibernate.cache.UpdateTimestampsCache'
        eternal true
        overflowToDisk false
        maxElementsInMemory 1000
        timeToIdleSeconds 0
        timeToLiveSeconds 0
    }

    cache {
        name 'grailsBlocksCache'
        eternal false
        overflowToDisk true
        maxElementsInMemory 1000
        maxElementsOnDisk 10000
        timeToLiveSeconds 1800
        timeToIdleSeconds 600
    }
    cache {
        name 'grailsTemplatesCache'
        eternal false
        overflowToDisk true
        maxElementsInMemory 1000
        maxElementsOnDisk 100000
        timeToLiveSeconds 1800
        timeToIdleSeconds 600
    }
    cache {
        name 'features'
        timeToIdleSeconds 1800
        timeToLiveSeconds 1800
    }
    cache {
        name 'permissions'
        timeToIdleSeconds 1800
        timeToLiveSeconds 1800
    }
    cache {
        name 'crmMessageCache'
        eternal false
        overflowToDisk true
        maxElementsInMemory 10000
        maxElementsOnDisk 100000
        timeToIdleSeconds 600
        timeToLiveSeconds 600
    }
    cache {
        name 'textTemplate'
    }
    cache {
        name 'content'
    }
}
