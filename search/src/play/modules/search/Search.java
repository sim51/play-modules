package play.modules.search;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.util.Version;

import play.Logger;
import play.Play;
import play.classloading.ApplicationClassloader;
import play.exceptions.UnexpectedException;
import play.modules.search.store.Store;

/**
 * Very basic tool to basic search on your JPA objects.
 * <p/>
 * On a JPABase subclass, add the @Indexed annotation on your class, and the @Field
 * annotation on your field members
 * <p/>
 * Each time you save, update or delete your class, the corresponding index is
 * updated
 * <p/>
 * use the search method to query an index.
 * <p/>
 * Samples in samples-and-tests/app/controllers/JPASearch.java
 */
public class Search {

    private static String ANALYSER_CLASS;
    private static Version LUCENE_VERSION;
    private static Store store;

    public static void init() {
        try {
            shutdown();
        } catch (Exception e) {
            Logger.error(e, "Error while shutting down search module");
        }
        ANALYSER_CLASS = Play.configuration.getProperty("play.search.analyser", "org.apache.lucene.analysis.standard.StandardAnalyzer");
        LUCENE_VERSION = Version.valueOf("LUCENE_" + Play.configuration.getProperty("play.search.lucene.version", "30"));
        String storeClassName = Play.configuration.getProperty("play.search.store","play.modules.search.store.FilesystemStore");
        try {
            store = (Store) Class.forName(storeClassName).newInstance();
            store.start();
        } catch (Exception e) {
            throw new UnexpectedException("Could not intialize store",e);
        }
    }

    @SuppressWarnings("unchecked")
    public static Analyzer getAnalyser() {
        Class<Analyzer> clazz = null;
        try {
            clazz = (Class<Analyzer>) Play.classloader.loadClass(ANALYSER_CLASS);
        } catch (ClassNotFoundException e) {
            throw new UnexpectedException("The analyzer class '" + ANALYSER_CLASS + "' could not be found!", e);
        }
        try {
            try {
                return clazz.getConstructor(Version.class).newInstance(getLuceneVersion());
            } catch (NoSuchMethodException e) {
                Logger.debug("getAnalyser(): not versioned constructor for class '%s', trying default constructor...", ANALYSER_CLASS);
                return clazz.getConstructor().newInstance();
            }
        } catch (Exception e) {
            throw new UnexpectedException("The analyzer class '" + ANALYSER_CLASS + "' could not be instanciated!", e);
        }
    }
    
    public static Version getLuceneVersion() {
        return LUCENE_VERSION;
    }

    public static Store getCurrentStore () {
        return store;
    }
    
    @SuppressWarnings("unchecked")
    public static Query search(String query, Class clazz) {
        return new Query(query, clazz, store);
    }

    public static void unIndex(Object object) {
        store.unIndex(object);
    }

    public static void index(Object object) {
        store.index(object,object.getClass().getName());
    }

    public static void rebuildAllIndexes () throws Exception {
        store.rebuildAllIndexes();
    }

    public static void shutdown() throws Exception {
        if (store!=null)
            store.stop();
    }
}
