package eu.heliovo.dpas.ie.classad;

import java.util.HashMap;
import java.util.Iterator;

import condor.classad.RecordExpr;

/**
 * @author pierantg
 * 
 */
public class ClassAdMapper {
	private static final long serialVersionUID = -8462007192633968715L;
	private HashMap<RecordExpr, Object> map = new HashMap<RecordExpr, Object>();
	transient private SerializationUtilities serUtils = new SerializationUtilities();
	transient private ClassAdUtilities cadUtils = new ClassAdUtilities();

	public void add(String key, Object value) throws ClassAdMapperException,
			ClassAdUtilitiesException {
		/*
		 * Check that key is not null
		 */
		if (key == null)
			throw new ClassAdMapperException();
		/*
		 * Check that key is not empty
		 */
		if (key.length() == 0)
			throw new ClassAdMapperException();
		/*
		 * Check if key is a valid ClassAd Expression.
		 */
		RecordExpr re = (RecordExpr) cadUtils.string2CompleteRecordExpr(key);
		if (re == null)
			throw new ClassAdMapperException();

		map.put(re, value);
	}

	public boolean isPresent(String key) throws ClassAdMapperException,
			ClassAdUtilitiesException {
		boolean result = false;
		/*
		 * Check that key is not null
		 */
		if (key == null)
			throw new ClassAdMapperException();
		/*
		 * Check if key is a valid ClassAd Expression.
		 */
		RecordExpr re = (RecordExpr) cadUtils.string2CompleteRecordExpr(key);
		if (re == null)
			throw new ClassAdMapperException();
		/*
		 * Look for all the record expressions that are keys
		 */
		Iterator<RecordExpr> i = map.keySet().iterator();
		while (i.hasNext()) {
			RecordExpr currKey = i.next();

			if (cadUtils.doMatch(re, currKey)) {
				// System.out.println(re + " matches with " + currKey);
				result = true;
			}
		}
		return result;
	}

	public ClassAdMapper getAll(String key) throws ClassAdMapperException,
			ClassAdUtilitiesException {
		ClassAdMapper res = new ClassAdMapper();

		Iterator<RecordExpr> i = map.keySet().iterator();

		RecordExpr re = (RecordExpr) cadUtils.string2CompleteRecordExpr(key);
		if (re == null)
			throw new ClassAdMapperException();

		RecordExpr currKey = null;

		while (i.hasNext()) {
			currKey = i.next();
			// System.out.println(currKey + " of type " + currKey.getClass());
			int[] r = cadUtils.match(re.toString(), currKey.toString());

			if (r != null) {
				res.add(currKey.toString(), map.get(currKey));
			}
		}
		return res;
	}

	public HashMap<Integer, Object> getAllSorted(String key) throws ClassAdMapperException,
			ClassAdUtilitiesException {
		HashMap<Integer, Object> res = new HashMap<Integer, Object>();

		Iterator<RecordExpr> i = map.keySet().iterator();

		RecordExpr re = (RecordExpr) cadUtils.string2CompleteRecordExpr(key);
		if (re == null)
			throw new ClassAdMapperException();

		RecordExpr currKey = null;

		while (i.hasNext()) {
			currKey = i.next();
			// System.out.println(currKey + " of type " + currKey.getClass());
			int[] r = cadUtils.match(re.toString(), currKey.toString());

			if (r != null) {
				res.put(r[0], map.get(currKey));
			}
		}
		return res;
	}

	public Object getBest(String key, int w_req, int w_map)
			throws ClassAdMapperException, ClassAdUtilitiesException {
		Object obj = null;
		/*
		 * Check that key is not null
		 */
		if (key == null)
			throw new ClassAdMapperException();
		/*
		 * Check that key is not empty
		 */
		if (key.length() == 0)
			throw new ClassAdMapperException();
		/*
		 * Check that the weights w_req and w_map siano corretti [0; 1].
		 */
		if ((w_req < 0) || (w_req > 1) || (w_map < 0) || (w_map > 1))
			throw new ClassAdMapperException();
		/*
		 * Check if key is a valid ClassAd Expression.
		 */
		RecordExpr re = (RecordExpr) cadUtils.string2CompleteRecordExpr(key);
		if (re == null)
			throw new ClassAdMapperException();
		// RecordExpr re = (RecordExpr) cadUtils.string2Expr(key);
		// if(re == null)
		// throw new ClassAdMapperException();

		/*
		 * Look for all the record expressions that are keys and create a vector
		 */
		Iterator<RecordExpr> i = map.keySet().iterator();
		int maxRank = 0;
		int currRank = 0;

		while (i.hasNext()) {
			RecordExpr currKey = i.next();
			// System.out.println(currKey.toString());

			int[] r = cadUtils.match(re, currKey);

			if (r != null) {
				// System.out.println(re.toString() + " <-> " +
				// currKey.toString() + " ==> " + currRank);
				currRank = w_req * r[0] + w_map * r[1];

				if (currRank > maxRank) {
					obj = map.get(currKey);
					maxRank = currRank;
				}
			}
		}
		return obj;
	}

	public Object getBest(String key) throws ClassAdMapperException,
			ClassAdUtilitiesException {
		return getBestForReq(key);
	}

	public Object getBestForReq(String key) throws ClassAdMapperException,
			ClassAdUtilitiesException {
		return getBest(key, 1, 0);
	}

	public Object getBestForMap(String key) throws ClassAdMapperException,
			ClassAdUtilitiesException {
		return getBest(key, 0, 1);
	}

	public String toString() {
		String result = new String();

		if (map.keySet().isEmpty()) {
			result = "[]";
		} else {
			Iterator<RecordExpr> iter = map.keySet().iterator();
			while (iter.hasNext()) {
				Object key = iter.next();
				Object obj = map.get(key);
				result += "[ ";
				result += key;
				result += " --> ";
				result += obj;
				result += " ]";
				result += "\n";
			}
		}

		return result;
	}

	public boolean isObjectPresent(Object o) {
		return map.containsValue(o);
	}

	public void saveStatus(String fileName) {
		/*
		 * Not all the map can be saved. If the key is a RecordExpr Do it only
		 * with serializable objects.
		 */
		HashMap<String, Object> serializableMap = new HashMap<String, Object>();
		Iterator<RecordExpr> i = map.keySet().iterator();
		while (i.hasNext()) {
			Object k = i.next();
			if (serUtils.isSerializable(map.get(k)))
				serializableMap.put(k.toString(), map.get(k));
		}
		serUtils.writeTo(serializableMap, fileName);
	}

	public void loadStatus(String fileName) throws ClassAdMapperException,
			ClassAdUtilitiesException {
		/*
		 * The recovered map may not be complete !!!
		 */
		HashMap<?, ?> serializedMap = (HashMap<?, ?>) serUtils
				.readFrom(fileName);

		map = new HashMap<RecordExpr, Object>();
		Iterator<?> i = serializedMap.keySet().iterator();
		while (i.hasNext()) {
			String k = (String) i.next();
			Object o = serializedMap.get(k);
			add(k, o);
		}
	}
}
