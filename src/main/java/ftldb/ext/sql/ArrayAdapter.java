/*
 * Copyright 2014-2015 Victor Osolovskiy, Sergey Navrotskiy
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package ftldb.ext.sql;


import freemarker.template.*;

import java.sql.Array;
import java.sql.SQLException;


/**
 * This class wraps {@link Array} and adapts it for using in FTL as a sequence.
 */
public class ArrayAdapter extends WrappingTemplateModel implements TemplateSequenceModel, AdapterTemplateModel {


    private final Array array;


    public ArrayAdapter(Array array, ObjectWrapper ow) {
        super(ow);
        this.array = array;
    }


    /**
     * Returns the wrapped SQL array.
     *
     * @return SQL array itself
     */
    public Object getAdaptedObject(Class hint) {
        return array;
    }


    /**
     * Retrieves the i-th element in this sequence.
     *
     * @return the item at the specified index
     */
    public TemplateModel get(int index) throws TemplateModelException {
        try {
            return wrap(((Object[]) array.getArray(index + 1, 1))[0]);
        } catch (SQLException e) {
            throw new TemplateModelException(e);
        }
    }


    /**
     * Returns the array size.
     *
     * @return the number of items in the list.
     */
    public int size() throws TemplateModelException {
        try {
            return ((Object[]) array.getArray()).length;
        } catch (SQLException e) {
            throw new TemplateModelException(e);
        }
    }


}